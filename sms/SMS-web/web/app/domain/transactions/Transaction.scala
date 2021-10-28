package domain.transactions

import domain.global.Address
import domain.products.Product
import services.{DeliveryMethod, TransporterApi}

import scala.concurrent.{ExecutionContext, Future}

case class Item(product: Product, quantity: Int) {
  def price: Double = product.salePrice * quantity
}

case class ItemWithProductPrice(override val product: Product, override val quantity: Int, purchasePrice: Double) extends Item(product, quantity) {
  override def price: Double = purchasePrice * quantity
}


sealed abstract class Transaction(val items: Seq[Item])(implicit val ec: ExecutionContext) {
  def totalPrice: Future[Double] = Future {
    items.foldLeft(0.0)((sum, item) => sum + item.price)
  }
}

case class Sale(override val items: Seq[Item], deliveryAddress: Address, deliveryMethod: DeliveryMethod)(override implicit val ec: ExecutionContext, implicit val transporterApi: TransporterApi) extends Transaction(items) {
  override def totalPrice: Future[Double] = for {
    itemsTotalPrice <- super.totalPrice
    deliveryCost <- transporterApi.getDeliveryCost(deliveryAddress, deliveryMethod)
  } yield itemsTotalPrice + deliveryCost
}

case class Purchase(override val items: Seq[Item])(override implicit val ec: ExecutionContext) extends Transaction(items) {

}
