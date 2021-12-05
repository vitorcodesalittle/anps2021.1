package model.transactions

import play.api.libs.json.{Json, OWrites}
import slick.lifted.MappedTo

import java.time.Instant

case class DeliveryMethod(value: String) extends MappedTo[String]

object DeliveryMethod {
  implicit val writes: OWrites[DeliveryMethod] = Json.writes[DeliveryMethod]
}

object Correios extends DeliveryMethod("Correios")
object JadLog extends DeliveryMethod("JadLog")

case class Sale(
                 transactionId: Option[TransactionId],
                 storeId: Int,
                 createdAt: Instant,
                 items: Option[Seq[Item]],
                 id: Option[Int],
                 deliveryMethod: DeliveryMethod,
                 deliveryPrice: Double,
                 deliveryAddress: Address)
  extends Transaction(transactionId, storeId, createdAt, items)

object Sale {
  implicit val writes: OWrites[Sale] = Json.writes[Sale]
}
