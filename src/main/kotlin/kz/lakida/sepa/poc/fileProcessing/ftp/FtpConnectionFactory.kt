package kz.lakida.sepa.poc.fileProcessing.ftp

import kz.lakida.sepa.poc.fileProcessing.util.LoggingOutputStream
import org.apache.commons.net.PrintCommandListener
import org.apache.commons.net.ftp.FTPClient
import org.apache.commons.net.ftp.FTPReply
import org.slf4j.LoggerFactory
import org.slf4j.event.Level
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.io.IOException
import java.io.PrintWriter

@Component
class FtpConnectionFactory(
    @Value("\${ftp.server}")
    private val server: String,
    @Value("\${ftp.port}")
    private val port: Int,
    @Value("\${ftp.user}")
    private val user: String,
    @Value("\${ftp.password}")
    private val password: String
) {
    fun createNewConnection(): FtpConnection {
        val ftp = FTPClient()

        ftp.addProtocolCommandListener(
            PrintCommandListener(
                PrintWriter(
                    LoggingOutputStream(
                        LoggerFactory.getLogger(FTPClient::class.java)
                    )
                )
            )
        )

        ftp.connect(server, port)

        val reply = ftp.replyCode
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect()
            throw IOException("Exception in connecting to FTP Server")
        }

        ftp.enterLocalPassiveMode()

        if (!ftp.login(user, password)) {
            ftp.disconnect()
            throw IOException("Could not login to ftp")
        }
        return FtpConnectionImpl(ftp)
    }
}
