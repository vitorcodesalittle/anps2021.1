package model.transactions

import slick.jdbc.PostgresProfile.api._
import slick.lifted.ProvenShape

import java.time.Instant

class Transactions(tag: Tag) extends Table[Sale](tag, "TRANSACTIONS") {
  def id: Rep[Int] = column[Int]("ID", O.PrimaryKey, O.AutoInc)

  def storeId: Rep[Int] = column[Int]("STORE_ID")

  def createdAt: Rep[Instant] = column[Instant]("CREATED_AT")

  def * : ProvenShape[Transaction] = (id.?, storeId, createdAt) <> (row ⇒ {
    Transaction(Some(TransactionId.from(row._1)), row._2, row._3, None)
  }, (transaction: Transaction) ⇒ {
    Some((Some(transaction.id.get.value), transaction.storeId, transaction.createdAt))
  })
}
