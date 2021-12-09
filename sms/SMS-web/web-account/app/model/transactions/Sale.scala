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
                 transactionStoreId: Int,
                 transactionCreatedAt: Instant,
                 transactionItems: Option[Seq[Item]],
                 deliveryMethod: DeliveryMethod,
                 deliveryPrice: Double,
                 deliveryAddress: Address)
  extends Transaction(transactionId, transactionStoreId, transactionCreatedAt, transactionItems)

object Sale {
  implicit val writes: OWrites[Sale] = Json.writes[Sale]
}
