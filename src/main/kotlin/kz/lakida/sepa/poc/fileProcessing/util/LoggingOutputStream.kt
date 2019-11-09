package kz.lakida.sepa.poc.fileProcessing.util

import org.slf4j.Logger
import org.slf4j.event.Level
import java.io.OutputStream

private typealias LogFunction = (String) -> Unit

class LoggingOutputStream(
    log: Logger,
    level: Level = Level.DEBUG
) : OutputStream() {
    private val maxLineLength = 255

    private val nextLog = ThreadLocal.withInitial { StringBuilder() }

    private val logger: LogFunction = when (level) {
        Level.ERROR -> log::error
        Level.WARN -> log::warn
        Level.INFO -> log::info
        Level.DEBUG -> log::debug
        Level.TRACE -> log::trace
    }

    override fun write(b: Int) {
        val stringBuilder = nextLog.get()
        stringBuilder.append(b.toChar())

        if (b == '\n'.toInt() || stringBuilder.length > maxLineLength) {
            logger.invoke(stringBuilder.toString().trim())
            nextLog.set(java.lang.StringBuilder())
        }
    }
}
