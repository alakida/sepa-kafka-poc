package kz.lakida.sepa.poc.fileProcessing

import kz.lakida.sepa.poc.fileProcessing.model.Transaction
import org.apache.kafka.clients.producer.Callback
import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.ProducerRecord
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.UUID
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

private const val NEW_TRANSACTIONS_TOPIC = "new_transactions"

@Service
class ProducerService(
    private val producer: Producer<String, Any>
) {
    private val log = LoggerFactory.getLogger(this::class.java)

    fun send(transactions: List<Transaction>) {
        val latch = CountDownLatch(transactions.size)

        try {
            transactions.forEach {
                producer.send(
                    ProducerRecord(NEW_TRANSACTIONS_TOPIC, UUID.randomUUID().toString(), it),
                    Callback { metadata, exception ->
                        if (exception != null) {
                            log.error("Could not send data", exception)
                        } else {
                            log.trace("Data was sent {}", metadata)
                        }
                        latch.countDown()
                    })
            }
            if (!latch.await(30, TimeUnit.SECONDS)) {
                throw Exception("Could not send all records")
            }
        } finally {
            producer.flush()
        }
    }
}
