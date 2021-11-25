package model.transactions

import play.api.libs.functional.syntax.unlift
import play.api.libs.json.{JsPath, Json, OWrites, Reads}
import slick.lifted.MappedTo

import java.time.Instant

case class TransactionId(value: Int) extends MappedTo[Int]

object TransactionId {
  implicit val reads: Reads[TransactionId] = Json.reads[TransactionId]
  implicit val writes: OWrites[TransactionId] = Json.writes[TransactionId]
}

abstract class Transaction(
                                 transactionId: Option[TransactionId],
                                 storeId: Int,
                                 createdAt: Instant,
                                 items: Option[Seq[Item]])
