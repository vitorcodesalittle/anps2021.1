package model.transactions

import model.dao.Tables.{ItemsRow, SalesRow, TransactionsRow}
import play.api.libs.json.Json

import java.time.Instant

case class Sale(transactionId: Int,
                storeId: Int,
                createdAt: Instant,
                id: Int,
                items: Option[Seq[Item]],
                deliveryMethod: String,
                deliveryPrice: Double,
                address: Address) extends Transaction(transactionId, storeId, createdAt) {
}

object Sale {
  implicit val reads = Json.reads[Sale]
  implicit val writes = Json.writes[Sale]

  def from(saleRow: SalesRow, transactionRow: TransactionsRow, itemsRow: Seq[ItemsRow]): Sale = Sale(
    transactionId = transactionRow.id.get,
    createdAt = transactionRow.createdAt.toInstant,
    storeId = transactionRow.storeId,
    id = saleRow.id.get,
    deliveryPrice =  saleRow.deliveryPrice,
    deliveryMethod = saleRow.deliveryMethod,
    items = Some(itemsRow map Item.from),
    address = Address(
      country = saleRow.addressCountry,
      state = saleRow.addressState,
      city = saleRow.addressCity,
      street = saleRow.addressStreet,
      zip = saleRow.addressZip,
      extra = saleRow.addressExtra
    )
  )

//  def from(saleRow: SalesRow, transactionRow: TransactionsRow): Sale = Sale(
//    transactionId = transactionRow.id,
//    createdAt = transactionRow.createdAt.toInstant,
//    storeId = transactionRow.storeId,
//    id = saleRow.id,
//    deliveryPrice =  saleRow.deliveryPrice,
//    deliveryMethod = saleRow.deliveryMethod,
//    items = None,
//    address = Address(
//      country = saleRow.addressCountry,
//      state = saleRow.addressState,
//      city = saleRow.addressCity,
//      street = saleRow.addressStreet,
//      zip = saleRow.addressZip,
//      extra = saleRow.addressExtra
//    )
//  )
}