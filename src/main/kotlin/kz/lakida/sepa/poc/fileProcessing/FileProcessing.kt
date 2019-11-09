package kz.lakida.sepa.poc.fileProcessing

import kz.lakida.sepa.poc.fileProcessing.ftp.WithFtp
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class FileProcessing(
    private val newTransactionsService: NewTransactionsService,
    private val transactionProcessingService: TransactionProcessingService
) {
    private val log = LoggerFactory.getLogger(this::class.java)

    @Scheduled(cron = "\${fileProcessing.job.schedule}")
    @WithFtp
    fun processingJob() {
        log.info("Job started")
        val newTransactions = newTransactionsService.fetch()

        transactionProcessingService.process(newTransactions)
        log.info("Job finished")
    }
}
