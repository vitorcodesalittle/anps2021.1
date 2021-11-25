package model.transactions

import model.store.{Store, Stores}
import slick.jdbc.PostgresProfile.api._
import slick.lifted.{ForeignKeyQuery, ProvenShape}

import java.time.Instant

class Purchases(tag: Tag) extends Table[Purchase](tag, "PURCHASES") {

  lazy val stores = TableQuery[Stores]

  def id: Rep[Int] = column[Int]("ID", O.PrimaryKey, O.AutoInc)

  def transactionId: Rep[TransactionId] = column[TransactionId]("TRANSACTION_ID", O.AutoInc)

  def createdAt: Rep[Instant] = column[Instant]("CREATED_AT")

  def storeId: Rep[Int] = column[Int]("STORE_ID")

  def store: ForeignKeyQuery[Stores, Store] = foreignKey("STORE", storeId, stores)(_.id)

  def * : ProvenShape[Purchase] = (id.?, transactionId, storeId, createdAt) <> (row => {
    val (id, transactionId, storeId, createdAt) = row
    Purchase(Some(transactionId), storeId, createdAt, None, id)
  }, (purchase: Purchase) => {
    Some((purchase.id, purchase.transactionId.get, purchase.storeId, purchase.createdAt))
  })

}
