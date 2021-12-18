package model.transactions.forms

import play.api.libs.json.Json

case class ItemData(productId: Int, quantity: Int, description: Option[String], price: Double)

object ItemData {
  implicit val reads = Json.reads[ItemData]
  implicit val writes = Json.writes[ItemData]
}
