package model.transactions

import java.time.Instant

case class Purchase(
                     override val transactionId: TransactionId,
                     override val storeId: Int,
                     override val createdAt: Instant,
                     override val items: Option[Seq[Item]],
                     id: Int
) extends Transaction(transactionId, storeId, createdAt, items)