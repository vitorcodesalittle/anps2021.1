package model.transactions

import model.services.transporters.TransporterAPI
import model.transactions.errors.InsufficientStock
import model.transactions.forms.{ItemData, PurchaseData, SaleData}
import play.api.libs.ws.WSClient

import java.time.Instant
import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class TransactionControl @Inject()(val repo: TransactionRepositoryRDB, val transporterService: TransporterAPI)
                                  (implicit val ec: ExecutionContext, implicit val ws: WSClient) {
  def getTransactions(from: Instant, to: Instant): Future[Seq[Transaction]] = {
    repo.getTransactions(from, to)
  }
  // TODO
  def getMissingQuantities(items: Seq[ItemData]): Future[Map[Int,Int]] = Future.successful(Map[Int, Int]())
  def createSale(data: SaleData): Future[Sale] = {
    getMissingQuantities(data.itemsData)
      .flatMap { map => {
        if (map.isEmpty) {
          transporterService.getDeliveryCost(data.address, data.deliveryMethod).
            flatMap(response => repo.createSale(data, response.price))
        } else {
          Future.failed(new InsufficientStock("Not enough stock", map))
        }
      }}
  }

  def createPurchase(data: PurchaseData): Future[Purchase] = {
    // create purchase and return
    repo.createPurchase(data)
  }
}
