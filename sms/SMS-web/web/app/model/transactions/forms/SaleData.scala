package model.transactions.forms

import model.global.Address
import model.store.forms.TransactionData
import play.api.libs.json.{Json, Reads}


case class SaleData(deliveryAddress: Address, deliveryMethod: String, transactionData: TransactionData)

object SaleData {
  implicit val saleDataReads: Reads[SaleData] = Json.reads[SaleData]
}
