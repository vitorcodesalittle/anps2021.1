package model.store.forms

import model.transactions.Item
import play.api.libs.json.{Json, Reads}


case class TransactionData(items: Seq[Item])

object TransactionData {

  implicit val transactionDataReads: Reads[TransactionData] = Json.reads[TransactionData]
}
