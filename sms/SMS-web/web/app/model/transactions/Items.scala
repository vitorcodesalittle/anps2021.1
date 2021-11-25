package model.transactions

import slick.jdbc.PostgresProfile.api._
import slick.lifted.ProvenShape

class Items(tag: Tag) extends Table[Item](tag, "ITEMS") {
  def id: Rep[Int] = column[Int]("ID", O.PrimaryKey, O.AutoInc)

  def transactionId: Rep[TransactionId] = column[TransactionId]("TRANSACTION_ID")

  def productId: Rep[Int] = column[Int]("PRODUCT_ID")

  def quantity: Rep[Int] = column[Int]("QUANTITY")

  def price: Rep[Double] = column[Double]("PRICE")

  def * : ProvenShape[Item] = (id.?, transactionId.?, productId, quantity, price) <> ((row) => {
    val (id, transactionId, productId, quantity, price) = row
    Item(id, transactionId, productId, quantity, price, None)
  }, (item: Item) => {
    Some((item.id, item.transactionId, item.productId, item.quantity, item.price))
  })

}
