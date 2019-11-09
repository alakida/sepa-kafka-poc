package kz.lakida.sepa.poc.fileProcessing.ftp

import org.springframework.stereotype.Service

@Service
class FileService(private val ftpConnectionHolder: FtpConnectionHolder) {
    fun listFiles(): List<String> = ftpConnectionHolder.get().listFiles()
    fun retrieveFile(fileName: String): String = ftpConnectionHolder.get().retrieveFile(fileName)
    fun deleteFile(fileName: String) {
        ftpConnectionHolder.get().deleteFile(fileName)
    }
}
