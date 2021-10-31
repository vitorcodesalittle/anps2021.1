package domain.transactions

import scala.concurrent.{ExecutionContext, Future}

abstract class Transaction(id: Int, items: Seq[SaleItem])(implicit val ec: ExecutionContext) {
  def totalPrice: Future[Double] = Future {
    items.foldLeft(0.0)((sum, item) => sum + item.price)
  }
}

