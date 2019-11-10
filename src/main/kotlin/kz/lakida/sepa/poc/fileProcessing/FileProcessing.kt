package kz.lakida.sepa.poc.fileProcessing

import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class FileProcessing(
    private val newTransactionsProcessingService: NewTransactionsProcessingService
) {
    private val log = LoggerFactory.getLogger(this::class.java)

    @Scheduled(cron = "\${fileProcessing.job.schedule}")
    fun processingJob() {
        log.info("Job started")

        newTransactionsProcessingService.process()

        log.info("Job finished")
    }
}
