package kz.lakida.sepa.poc.fileProcessing

import kz.lakida.sepa.poc.fileProcessing.model.Transaction
import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.ProducerRecord
import org.springframework.stereotype.Service
import java.util.UUID

private const val NEW_TRANSACTIONS_TOPIC = "new_transactions"

@Service
class TransactionProcessingService(
    private val producer: Producer<String, Any>
) {
    fun process(transactions: List<Transaction>) {
        transactions.forEach {
            producer.send(ProducerRecord(NEW_TRANSACTIONS_TOPIC, UUID.randomUUID().toString(), it))
        }
    }
}
