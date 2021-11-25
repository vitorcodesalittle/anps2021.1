package model.transactions

import java.time.Instant

case class Purchase(
                     transactionId: Option[TransactionId],
                     storeId: Int,
                     createdAt: Instant,
                     items: Option[Seq[Item]],
                     id: Option[Int]
) extends Transaction(transactionId, storeId, createdAt, items)