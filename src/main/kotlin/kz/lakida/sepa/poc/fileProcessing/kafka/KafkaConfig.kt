package kz.lakida.sepa.poc.fileProcessing.kafka

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.Properties

@Configuration
class KafkaConfig(
    @Value("\${kafka.brokers}")
    private val brokers: String,
    @Value("\${kafka.clientId}")
    private val clientId: String
) {

    @Bean
    fun newTransactionProducer(): Producer<String, Any> {
        val properties = Properties()
        properties[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = brokers
        properties[ProducerConfig.CLIENT_ID_CONFIG] = clientId
        properties[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java.name
        properties[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = JsonSerializer::class.java.name

        return KafkaProducer(properties)
    }
}
