package model.transactions.forms

import model.transactions.Address
import play.api.libs.json.{Json, OWrites, Reads}


case class ItemData(productId: Int, quantity: Int)

object ItemData {
  implicit val reads: Reads[ItemData] = Json.reads[ItemData]
  implicit val writes: OWrites[ItemData] = Json.writes[ItemData]
}

case class SaleData(deliveryAddress: Address, deliveryMethod: String, items: Seq[ItemData])

object SaleData {
  implicit val saleDataReads: Reads[SaleData] = Json.reads[SaleData]
}
