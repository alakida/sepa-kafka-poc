package kz.lakida.sepa.poc.fileProcessing

import com.fasterxml.jackson.databind.ObjectMapper
import kz.lakida.sepa.poc.fileProcessing.ftp.FileService
import kz.lakida.sepa.poc.fileProcessing.model.Transaction
import kz.lakida.sepa.poc.fileProcessing.model.Transactions
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class NewTransactionsService(
    private val fileService: FileService,
    private val objectMapper: ObjectMapper
) {
    private val log = LoggerFactory.getLogger(this::class.java)

    fun fetch(): List<Transaction> {
        val fileList = fileService.listFiles()
        if (fileList.isNotEmpty()) {
            log.info("Found files {}", fileList)
        } else {
            log.info("No files found, skipping")
            return emptyList()
        }

        val transactions = fileList.asSequence()
            .map { fileService.retrieveFile(it) }
            .map {
                objectMapper.readValue(
                    it,
                    Transactions::class.java
                )
            }
            .flatMap { it.transactions.asSequence() }
            .toList()

        if (transactions.isNotEmpty()) {
            log.info("Found {} transactions", transactions.size)
            log.trace("{}", transactions)
        }

        fileList.forEach { fileService.deleteFile(it) }

        return transactions
    }
}
