package model.transactions

import play.api.libs.json.{Json, Reads}

case class Item(id: Option[Int], transactionId: Option[Int], productId: Int, quantity: Int, price: Double, product: Option[Product])

object Item {
  implicit val itemReads: Reads[Item] = Json.reads[Item]
}
