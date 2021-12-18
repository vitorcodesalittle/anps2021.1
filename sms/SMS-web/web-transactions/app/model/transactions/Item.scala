package model.transactions

import model.dao.Tables.ItemsRow
import play.api.libs.json.Json

case class Item(productId: Int, quantity: Int, description: Option[String], saleId: Int, price: Double)

object Item {
  implicit val reads = Json.reads[Item]
  implicit val writes = Json.writes[Item]

  def from (row: ItemsRow): Item = Item(
    productId =  row.productId,
    saleId = row.saleId,
    description = row.description,
    price = row.price,
    quantity = row.quantity
  )
}
