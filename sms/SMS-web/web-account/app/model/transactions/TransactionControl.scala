package model.transactions

import model.products.ProductRepositoryRDB
import model.services.session.UserInfo
import model.services.transporter.{Transporter, TransporterApi}
import model.transactions.forms.{CacheFlowRequestData, SaleData}
import play.api.libs.ws.WSClient
import slick.dbio.DBIO

import java.time.Instant
import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class TransactionControl @Inject()(repo: TransactionRepositoryRDB, productsRepo: ProductRepositoryRDB)(implicit val ec: ExecutionContext, ws: WSClient) extends TransporterApi {
  def getSales(userInfo: UserInfo): Future[Seq[Sale]] = {
    repo.run(repo.getAllSales(userInfo.storeId))
  }


  def getTransactions(userInfo: UserInfo): Future[Seq[Transaction]] = {
    repo.run(repo.getAllTransactions(userInfo.storeId))
  }

  def doSale(saleData: SaleData, userInfo: UserInfo): Future[Sale] = {
    productsRepo.run(for {
      products <- productsRepo.getById(saleData.items.map(_.productId))
      hasSufficientStock <- DBIO.successful(
        (products zip saleData.items).foldLeft(true)((acc, pair) => {
          val (product, item) = pair
          acc && (product.stock >= item.quantity)
        })
      )
      if hasSufficientStock
      deliveryPrice ← DBIO.from(getDeliveryCost(saleData.deliveryAddress, Transporter(saleData.deliveryMethod)) map (_.price) )
      sale <- repo.createSale(
        Sale(None, userInfo.storeId, Instant.now(), Some((saleData.items zip products.map(_.suggestedPrice)).map(pair => {
          val (itemData, price) = pair
          Item(None, None, itemData.productId, itemData.quantity, price, None)
        })), None, DeliveryMethod(saleData.deliveryMethod), deliveryPrice, saleData.deliveryAddress)
      )
      _ <- productsRepo.decrementStock(saleData.items)
    } yield sale)
  }

  def mountCashFlow(cacheFlowRequestData: CacheFlowRequestData, userInfo: UserInfo): Future[Seq[Option[Transaction]]] = ???

}
