package model.transactions

import model.transactions.forms.{PurchaseData, SaleData}

import java.time.Instant
import scala.concurrent.Future

trait TransactionsRepository {
  def createSale(saleData: SaleData, deliveryPrice: Double): Future[Sale]
  def createPurchase(purchaseData: PurchaseData): Future[Purchase]
  def getTransactions(from: Instant, to: Instant): Future[Seq[Transaction]]
}
