package model.transactions

import model.services.session.UserInfo
import model.transactions.forms.{CacheFlowRequestData, SaleData}

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext
import scala.util.Try

@Singleton
class TransactionControl @Inject()(repo: TransactionRepositoryRDB, implicit val ec: ExecutionContext) {
  def getTransactions(userInfo: UserInfo): Try[Seq[(Transaction, Option[Sale], Option[Transaction], Seq[Item])]] = ???

  def mountCashFlow(cacheFlowRequestData: CacheFlowRequestData, userInfo: UserInfo): Try[Seq[Option[(Transaction, Seq[Item])]]] = ???

  def doSale(saleData: SaleData, userInfo: UserInfo): Try[(Transaction, Sale, Seq[Item])] = ???
}
