package kz.lakida.sepa.poc.fileProcessing.ftp

interface FtpConnection : AutoCloseable {
    fun retrieveFile(fileName: String): String
    fun listFiles(): List<String>
    fun deleteFile(fileName: String)
}
