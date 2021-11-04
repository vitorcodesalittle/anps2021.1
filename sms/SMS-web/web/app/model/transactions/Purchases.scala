package model.transactions

import slick.jdbc.PostgresProfile.api._

class Purchases(tag: Tag) extends Table[Purchase](tag, "PURCHASES") {

  def id: Rep[Int] = column[Int]("ID", O.PrimaryKey, O.AutoInc)
  def transactionId: Rep[Int] = column[Int]("TRANSACTION_ID", O.PrimaryKey, O.AutoInc)
  def * = (id.?, transactionId) <> (Purchase.tupled, Purchase.unapply)

}
