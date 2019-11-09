package kz.lakida.sepa.poc.jackson

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class ZonedDateTimeDeserializer(
    private val formatter: DateTimeFormatter
) : JsonDeserializer<ZonedDateTime>() {

    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): ZonedDateTime {
        val stringDate = p.valueAsString
        return ZonedDateTime.parse(stringDate, formatter)
    }

    override fun handledType() = ZonedDateTime::class.java
}
