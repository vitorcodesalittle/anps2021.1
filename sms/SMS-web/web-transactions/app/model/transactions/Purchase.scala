package model.transactions

import play.api.libs.json.Json

import java.time.Instant

case class Purchase(transactionId: Int, storeId: Int, createdAt: Instant, description: String) extends Transaction(transactionId, storeId, createdAt) {

}
object Purchase {
  implicit val reads = Json.reads[Purchase]
  implicit val writes = Json.writes[Purchase]
}