package kz.lakida.sepa.poc.fileProcessing.ftp

import org.apache.commons.net.ftp.FTPClient
import org.slf4j.LoggerFactory
import java.io.IOException

class FtpConnectionImpl(
    private val ftpClient: FTPClient
) : FtpConnection {
    private val log = LoggerFactory.getLogger(this::class.java)

    override fun retrieveFile(fileName: String): String {
        log.debug("Attempt to download file {}", fileName)

        val content = ftpClient.retrieveFileStream(fileName).bufferedReader().use {
            it.readText()
        }
        if (!ftpClient.completePendingCommand()) {
            throw IOException("Could not complete file transfer $fileName")
        }

        log.trace("File content\n{}", content)
        return content
    }

    override fun listFiles(): List<String> {
        return ftpClient.listFiles("").map { it.name }
    }

    override fun deleteFile(fileName: String) {
        if (!ftpClient.deleteFile(fileName)) {
            throw IOException("Could not delete file $fileName")
        }
    }

    override fun close() {
        log.trace("Disconnecting ftp")
        try {
            ftpClient.disconnect()
            log.debug("FTP disconnected")
        } catch (e: Exception) {
            log.warn("Could not close ftp client", e)
        }
    }
}
