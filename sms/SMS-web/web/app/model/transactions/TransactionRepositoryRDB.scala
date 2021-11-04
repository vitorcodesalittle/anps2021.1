package model.transactions

import java.time.LocalDateTime
import javax.inject.{Inject, Singleton}
import scala.concurrent.Future

@Singleton
class TransactionRepositoryRDB @Inject extends TransactionRepository {
  override def getTransactions(since: LocalDateTime, to: LocalDateTime): Future[Seq[Transaction]] = ???

  override def createSale(sale: Sale): Future[Sale] = ???

  override def getSales(since: LocalDateTime, to: LocalDateTime): Future[Seq[Sale]] = ???

  override def getPurchases(since: LocalDateTime, to: LocalDateTime): Future[Seq[Purchase]] = ???

  override def createPurchase(purchase: Purchase): Future[Purchase] = ???

  override def deleteSale(saleId: Int): Future[Int] = ???
}
