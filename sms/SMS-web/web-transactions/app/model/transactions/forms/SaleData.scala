package model.transactions.forms

import model.transactions.Address
import play.api.libs.json.Json

case class SaleData(itemsData: Seq[ItemData], deliveryMethod: String, address: Address, storeId: Int)

object SaleData {
  implicit val reads = Json.reads[SaleData]
  implicit val writes = Json.writes[SaleData]
}
