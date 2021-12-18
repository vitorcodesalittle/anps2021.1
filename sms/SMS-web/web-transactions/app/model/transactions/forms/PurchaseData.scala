package model.transactions.forms

import play.api.libs.json.Json

case class PurchaseData(description: String)

object PurchaseData {
  implicit val reads = Json.reads[PurchaseData]
  implicit val writes =  Json.writes[PurchaseData]
}