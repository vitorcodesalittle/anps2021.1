package model.transactions

import java.time.Instant

case class Purchase(
                     override val transactionId: Option[TransactionId],
                     override val storeId: Int,
                     override val createdAt: Instant,
                     override val items: Option[Seq[Item]],
                     id: Option[Int]
) extends Transaction(transactionId, storeId, createdAt, items)