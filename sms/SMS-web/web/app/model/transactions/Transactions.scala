package model.transactions

import model.store.{Store, Stores}
import slick.jdbc.PostgresProfile.api._
import slick.lifted.{ForeignKeyQuery, ProvenShape, Tag}

import java.time.Instant

class Transactions(tag: Tag) extends Table[Transaction](tag, "TRANSACTIONS") {
  def id: Rep[Int] = column[Int]("ID", O.PrimaryKey, O.AutoInc)

  def createdAt: Rep[Instant] = column[Instant]("CREATED_AD")

  def storeId: Rep[Int] = column[Int]("STORE_ID")

  def store: ForeignKeyQuery[Stores, Store] = foreignKey("STORE", storeId, stores)(_.id)

  override def * : ProvenShape[Transaction] = (id.?, storeId, createdAt) <> (row => {
    val (id, storeId, createdAt) = row
    Transaction(TransactionId(id.get), storeId, createdAt, None)
  }, (transaction: Transaction) => {
    Some((Some(transaction.transactionId.value), transaction.storeId, transaction.createdAt))
  })

  lazy val stores = TableQuery[Stores]
}
