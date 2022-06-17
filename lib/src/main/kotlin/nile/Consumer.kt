package nile

import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.consumer.KafkaConsumer
import java.time.Duration
import java.util.Properties

class Consumer(servers: String, groupId: String, private val topic: String) {
    companion object {
        fun createConfig(servers: String, groupId: String): Properties {
            val props = Properties()
            props["bootstrap.servers"] = servers
            props["group.id"] = groupId
            props["enable.auto.commit"] = "true"
            props["auto.commit.interval.ms"] = "1000"
            props["auto.offset.reset"] = "earliest"
            props["session.timeout.ms"] = "30000"
            props["key.deserializer"] = "org.apache.kafka.common.serialization.StringDeserializer"
            props["value.deserializer"] = "org.apache.kafka.common.serialization.StringDeserializer"

            return props
        }
    }

    private val consumer = KafkaConsumer<String, String>(createConfig(servers, groupId))

    fun run(producer: IProducer) {
        consumer.subscribe(listOf(topic))
        while (true) {
            val records: ConsumerRecords<String, String> = consumer.poll(Duration.ofMillis(100))
            records.forEach { record ->
                producer.process(record.value())
            }
        }
    }
}
