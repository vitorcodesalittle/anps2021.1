package model.transactions

import model.store.Stores
import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag

import java.time.Instant

class Transactions(tag: Tag) extends Table[Transaction](tag, "TRANSACTIONS") {
  def id: Rep[Int] = column[Int]("ID", O.PrimaryKey, O.AutoInc)

  def createdAt: Rep[Instant] = column[Instant]("CREATED_AD")

  def storeId: Rep[Int] = column[Int]("STORE_ID")

  def store = foreignKey("STORE", storeId, stores)(_.id)

  override def * = (id.?, storeId, createdAt) <> ((Transaction.apply _).tupled, Transaction.unapply)

  lazy val stores = TableQuery[Stores]
}
