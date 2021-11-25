package model.transactions

import model.products.Product
import play.api.libs.json.{Json, OWrites, Reads}

case class Item(id: Option[Int], transactionId: Option[TransactionId], productId: Int, quantity: Int, price: Double, product: Option[Product])

object Item {
  implicit val itemReads: Reads[Item] = Json.reads[Item]
  implicit val writes: OWrites[Item] = Json.writes[Item]
}
