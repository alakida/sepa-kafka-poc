package kz.lakida.sepa.poc.fileProcessing.model

import java.math.BigDecimal
import java.time.ZonedDateTime

data class Transactions(
    val transactions: List<Transaction>
)

data class Transaction(
    val originatorId: String,
    val recipientId: String,
    val amount: BigDecimal,
    val settlementDate: ZonedDateTime
)
