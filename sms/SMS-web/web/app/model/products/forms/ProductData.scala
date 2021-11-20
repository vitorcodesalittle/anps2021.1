package model.products.forms

import play.api.libs.json.{Json, OWrites, Reads}

case class ProductData(name: String, stock: Int, suggestedPrice: Int, barcode: String)


object ProductData {
  implicit val reads: Reads[ProductData] = Json.reads[ProductData]
  implicit val writes: OWrites[ProductData] = Json.writes[ProductData]
}
