package model.transactions

import java.time.Instant

case class Purchase(
                     override val transactionId: TransactionId,
                     override val storeId: Int,
                     override val createdAt: Instant,
                     id: Int
) extends Transaction(transactionId, storeId, createdAt)