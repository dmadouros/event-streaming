package nile

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import java.util.Properties

interface IProducer {
    companion object {
        fun write(producer: KafkaProducer<String, String>, topic: String, message: String) {
            val pr: ProducerRecord<String, String> = ProducerRecord(topic, message)
            producer.send(pr)
        }

        fun createConfig(servers: String): Properties {
            val props = Properties()
            props["bootstrap.servers"] = servers
            props["acks"] = "all"
            props["retries"] = 0
            props["batch.size"] = 1000
            props["linger.ms"] = 1
            props["key.serializer"] = "org.apache.kafka.common.serialization.StringSerializer"
            props["value.serializer"] = "org.apache.kafka.common.serialization.StringSerializer"

            return props
        }
    }

    fun process(message: String)
}
