package model.transactions

import model.services.session.UserInfo
import model.transactions.forms.{CacheFlowRequestData, SaleData}

import javax.inject.{Inject, Singleton}
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.util.Try

@Singleton
class TransactionControl @Inject()(repo: TransactionRepositoryRDB, implicit val ec: ExecutionContext) {
  def getTransactions(userInfo: UserInfo): Future[Seq[Transaction]] = {
    repo.run(repo.getAllTransactions(userInfo.storeId))
  }

  def doSale(saleData: SaleData, userInfo: UserInfo): Future[Sale] = {
    repo.run(
      repo.createSale(
        ???
      )
    )
  }

  def mountCashFlow(cacheFlowRequestData: CacheFlowRequestData, userInfo: UserInfo): Try[Seq[Option[(Transaction, Seq[Item])]]] = ???

}
