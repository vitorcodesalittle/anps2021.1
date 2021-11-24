package model.transactions

import slick.lifted.MappedTo

import java.time.Instant

case class DeliveryMethod(value: String) extends MappedTo[String]

case object Correios extends DeliveryMethod("Correios")
case object JadLog extends DeliveryMethod("JadLog")

case class Sale(
                 override val transactionId: TransactionId,
                 override val storeId: Int,
                 override val createdAt: Instant,
                 id: Int,
                 deliveryMethod: DeliveryMethod,
                 deliveryPrice: Double,
                 deliveryAddress: Address)
  extends Transaction(transactionId, storeId, createdAt)
