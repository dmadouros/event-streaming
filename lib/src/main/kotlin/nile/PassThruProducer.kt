package nile

import org.apache.kafka.clients.producer.KafkaProducer

class PassThruProducer(servers: String, private val topic: String) : IProducer {
    private val producer: KafkaProducer<String, String> = KafkaProducer(IProducer.createConfig(servers))

    override fun process(message: String) {
        IProducer.write(producer, topic, message)
    }
}
