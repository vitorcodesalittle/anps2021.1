package model.transactions

import scala.concurrent.ExecutionContext

case class Purchase(id: Int, transactionId: Int, items: Seq[SaleItem])(override implicit val ec: ExecutionContext) extends Transaction(transactionId, items) {

}
