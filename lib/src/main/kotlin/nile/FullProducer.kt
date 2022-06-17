package nile

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.maxmind.geoip2.DatabaseReader
import com.maxmind.geoip2.model.CityResponse
import org.apache.kafka.clients.producer.KafkaProducer
import org.intellij.lang.annotations.Language
import java.net.InetAddress

class FullProducer(servers: String, private val goodTopic: String, val badTopic: String, val maxmind: DatabaseReader) :
    IProducer {
    companion object {
        val MAPPER = ObjectMapper().registerModule(KotlinModule.Builder().build())!!
    }

    private val producer: KafkaProducer<String, String> = KafkaProducer(IProducer.createConfig(servers))

    override fun process(message: String) {
        try {
            val root: JsonNode = MAPPER.readTree(message)
            val ipNode: JsonNode = root.path("shopper").path("ipAddress")
            if (ipNode.isMissingNode) {
                IProducer.write(producer, badTopic, "{\"error\": \"shopper.ipAddress missing\"}")
            } else {
                val ip: InetAddress = InetAddress.getByName(ipNode.textValue())
                val resp: CityResponse = maxmind.city(ip)
                (root as ObjectNode).with("shopper").put("country", resp.country.name)
                root.with("shopper").put("city", resp.city.name)
                IProducer.write(this.producer, this.goodTopic, MAPPER.writeValueAsString(root))
            }
        } catch (e: Exception) {
            @Language("JSON") val message = "{\"error\": \"${e::class.java.simpleName}: ${e.message}\"}"
            IProducer.write(producer, badTopic, message)
        }
    }
}
