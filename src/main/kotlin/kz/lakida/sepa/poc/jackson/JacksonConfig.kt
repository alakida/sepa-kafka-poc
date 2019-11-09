package kz.lakida.sepa.poc.jackson

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.format.DateTimeFormatter

@Configuration
class JacksonConfig {
    @Bean
    fun jacksonCustomizer() = Jackson2ObjectMapperBuilderCustomizer {
        it.serializers(ZonedDateTimeSerializer(DATE_FORMATTER))
        it.deserializers(ZonedDateTimeDeserializer(DATE_FORMATTER))
        it.featuresToDisable(
            SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
            DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE
        )
    }
}

internal const val DATE_FORMAT = "yyyy-MM-dd HH:mm:ssZ"
internal val DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT)
