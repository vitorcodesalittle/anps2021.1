package model.products

import play.api.libs.json.{Json, OWrites, Reads}

case class Product(id: Option[Int], name: String, suggestedPrice: Double, stock: Int, barcode: String, storeId: Int)

object Product {
  implicit val productWrites: OWrites[Product] = Json.writes[Product]
  implicit val productReads: Reads[Product] = Json.reads[Product]
}