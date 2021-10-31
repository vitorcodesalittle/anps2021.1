package domain.transactions

import java.time.LocalDateTime
import scala.concurrent.Future

trait TransactionRepository {
  def getSales(since: LocalDateTime, to: LocalDateTime): Future[Seq[Sale]]

  def getPurchases(since: LocalDateTime, to: LocalDateTime): Future[Seq[Purchase]]

  def getTransactions(since: LocalDateTime, to: LocalDateTime): Future[Seq[Transaction]]

  def createSale(sale: Sale): Future[Sale]

  def createPurchase(purchase: Purchase): Future[Purchase]

  def deleteSale(saleId: Int): Future[Sale]
}
