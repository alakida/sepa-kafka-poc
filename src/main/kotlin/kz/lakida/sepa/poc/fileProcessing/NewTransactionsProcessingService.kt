package kz.lakida.sepa.poc.fileProcessing

import com.fasterxml.jackson.databind.ObjectMapper
import kz.lakida.sepa.poc.fileProcessing.ftp.FileService
import kz.lakida.sepa.poc.fileProcessing.ftp.WithFtp
import kz.lakida.sepa.poc.fileProcessing.model.Transactions
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class NewTransactionsProcessingService(
    private val fileService: FileService,
    private val producerService: ProducerService,
    private val objectMapper: ObjectMapper
) {
    private val log = LoggerFactory.getLogger(this::class.java)

    @WithFtp
    fun process() {
        val fileList = fileService.listFiles()
        if (fileList.isNotEmpty()) {
            log.info("Found files {}", fileList)
            fileList.asSequence()
                .map { it to fileService.retrieveFile(it) }
                .map {
                    it.first to objectMapper.readValue(
                        it.second,
                        Transactions::class.java
                    )
                }
                .map {
                    val transactions = it.second.transactions

                    if (transactions.isNotEmpty()) {
                        producerService.send(transactions)
                    }

                    it
                }
                .map {
                    fileService.deleteFile(it.first)
                    it
                }
                .forEach {
                    log.info(
                        "File ${it.first} has been processed with ${it.second.transactions.size} " +
                                "transactions"
                    )
                }
        } else {
            log.info("No files found, skipping")
        }
    }
}
