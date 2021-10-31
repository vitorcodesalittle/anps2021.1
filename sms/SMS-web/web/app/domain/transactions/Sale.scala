package domain.transactions

import domain.global.Address

import scala.concurrent.{ExecutionContext, Future}
import services.{DeliveryMethod, TransporterApi}

case class Sale(id: Int, transactionId: Int, items: Seq[SaleItem], deliveryAddress: Address, deliveryMethod: DeliveryMethod)(override implicit val ec: ExecutionContext, implicit val transporterApi: TransporterApi) extends Transaction(transactionId, items) {
  override def totalPrice: Future[Double] = for {
    itemsTotalPrice <- super.totalPrice
    deliveryCost <- transporterApi.getDeliveryCost(deliveryAddress, deliveryMethod)
  } yield itemsTotalPrice + deliveryCost
}
