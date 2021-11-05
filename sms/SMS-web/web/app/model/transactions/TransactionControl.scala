package model.transactions

import model.products.Product
import model.services.session.UserInfo
import model.transactions.forms.{CacheFlowRequestData, SaleData}

import java.time.Instant
import javax.inject.{Inject, Singleton}
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext}
import scala.util.Try

@Singleton
class TransactionControl @Inject()(repo: TransactionRepositoryRDB, implicit val ec: ExecutionContext) {
  def getTransactions(userInfo: UserInfo): Try[Seq[(Transaction, Option[Sale], Option[Purchase], Seq[(Item, Product)])]] = {
    val transactionsFuture = repo.getAllTransactions(userInfo.storeId)
    Await.ready(transactionsFuture, Duration.Inf).value.get
  }

  def doSale(saleData: SaleData, userInfo: UserInfo): Try[(Transaction, Sale, Seq[Item])] = {
    val saleFuture = repo.createSaleWithTransaction(
      Transaction(None, userInfo.storeId, Instant.now),
      Sale(None, None, saleData.deliveryMethod, 0.0, saleData.deliveryAddress),
      saleData.transactionData.items
    )
    Await.ready(saleFuture, Duration.Inf).value.get
  }

  def mountCashFlow(cacheFlowRequestData: CacheFlowRequestData, userInfo: UserInfo): Try[Seq[Option[(Transaction, Seq[Item])]]] = ???

}