package model.transactions

import slick.jdbc.PostgresProfile.api._

class Items (tag: Tag) extends Table[Item](tag, "ITEMS") {
  def id: Rep[Int] = column[Int]("ID", O.PrimaryKey, O.AutoInc)
  def transactionId: Rep[Int] = column[Int]("TRANSACTION_ID")
  def productId: Rep[Int] = column[Int]("PRODUCT_ID")
  def quantity: Rep[Int] = column[Int]("QUANTITY")
  def price: Rep[Double] = column[Double]("PRICE")

  def * = (id.?, transactionId, productId, quantity, price) <> (Item.tupled, Item.unapply)

}
