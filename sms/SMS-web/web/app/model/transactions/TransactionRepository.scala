package model.transactions

import java.time.Instant
import scala.concurrent.Future

trait TransactionRepository {

  def getAllTransactions(storeId: Int): Future[Seq[(Transaction, Option[Sale], Option[Purchase], Seq[(Item, Product)])]]

  def getTransactions(storeId: Int, since: Instant, to: Instant): Future[Seq[(Transaction, Option[Sale], Option[Purchase], Seq[Item])]]

  def createSaleWithTransaction(transaction: Transaction, sale: Sale, items: Seq[Item]): Future[(Transaction, Sale, Seq[Item])]

  def createSale(sale: Sale): Future[Sale]

  def getSales(since: Instant, to: Instant): Future[Seq[Sale]]

  def getPurchases(since: Instant, to: Instant): Future[Seq[Purchase]]

  def createPurchase(purchase: Purchase): Future[Purchase]

  def deleteSale(saleId: Int): Future[Int]
}
