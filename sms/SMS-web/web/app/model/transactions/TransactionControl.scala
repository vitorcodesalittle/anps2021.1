package model.transactions

import model.products.ProductRepositoryRDB
import model.services.session.UserInfo
import model.transactions.forms.{CacheFlowRequestData, SaleData}

import java.time.Instant
import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

@Singleton
class TransactionControl @Inject()(repo: TransactionRepositoryRDB, productsRepo: ProductRepositoryRDB, implicit val ec: ExecutionContext) {
  def getSales(userInfo: UserInfo): Future[Seq[Sale]] = {
    repo.run(repo.getAllSales(userInfo.storeId))
  }

  def getTransactions(userInfo: UserInfo): Future[Seq[Transaction]] = {
    repo.run(repo.getAllTransactions(userInfo.storeId))
  }

  def doSale(saleData: SaleData, userInfo: UserInfo): Future[Sale] = {
    productsRepo.run(for {
      ok <- productsRepo.hasSufficientStock(saleData.items)
      if ok
      prices <- productsRepo.getPrices(saleData.items.map(_.productId))
      sale <- repo.createSale(
        Sale(None, userInfo.storeId, Instant.now(), Some((saleData.items zip prices).map(pair => {
          val (itemData, price) = pair
          Item(None, None, itemData.productId, itemData.quantity, price, None)
        })), None, DeliveryMethod(saleData.deliveryMethod), 3.3, saleData.deliveryAddress)
      )
    } yield sale)
  }

  def mountCashFlow(cacheFlowRequestData: CacheFlowRequestData, userInfo: UserInfo): Future[Seq[Option[Transaction]]] = ???

}
