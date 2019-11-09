package kz.lakida.sepa.poc.fileProcessing.ftp

import org.springframework.stereotype.Component

@Component
class FtpConnectionHolder {
    private val connections = ThreadLocal<FtpConnection>()

    fun put(connection: FtpConnection) {
        connections.set(connection)
    }

    fun get(): FtpConnection {
        return connections.get() ?: throw FtpConnectionNotFound()
    }

    fun remove() {
        connections.remove()
    }
}
