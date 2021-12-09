package model.transactions

import play.api.libs.json.{Json, OWrites, Reads}
import slick.lifted.MappedTo

import java.time.Instant

case class TransactionId(value: Int) extends MappedTo[Int]

object TransactionId {
  implicit val reads: Reads[TransactionId] = Json.reads[TransactionId]
  implicit val writes: OWrites[TransactionId] = Json.writes[TransactionId]

  def from(id: Option[Int]): TransactionId = id map TransactionId
}

case class Transaction(id: Option[TransactionId],
                  storeId: Int,
                  createdAt: Instant,
                  items: Option[Seq[Item]])
