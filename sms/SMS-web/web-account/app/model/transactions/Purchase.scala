package model.transactions

import java.time.Instant

case class Purchase(
                     transactionId: Option[TransactionId],
                     transactionStoreId: Int,
                     transactionCreatedAt: Instant,
                     transactionItems: Option[Seq[Item]],
) extends Transaction(transactionId, transactionStoreId, transactionCreatedAt, transactionItems)