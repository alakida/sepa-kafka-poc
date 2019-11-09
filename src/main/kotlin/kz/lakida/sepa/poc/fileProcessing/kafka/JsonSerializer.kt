package kz.lakida.sepa.poc.fileProcessing.kafka

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import kz.lakida.sepa.poc.jackson.DATE_FORMATTER
import org.apache.kafka.common.serialization.Serializer

class JsonSerializer : Serializer<Any> {
    private val objectMapper = jacksonObjectMapper().registerModule(
        JavaTimeModule().addSerializer(ZonedDateTimeSerializer(DATE_FORMATTER))
    ).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)

    override fun serialize(topic: String, data: Any): ByteArray = objectMapper.writeValueAsBytes(data)
}
