package model.transactions
import model.dao.Tables
import model.transactions.forms.{PurchaseData, SaleData}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.PostgresProfile
import slick.jdbc.PostgresProfile.api._

import java.sql.Timestamp
import java.time.Instant
import javax.inject.Inject
import scala.concurrent.{Await, ExecutionContext, Future}

class TransactionRepositoryRDB @Inject()(dbConfigProvide: DatabaseConfigProvider)
                                        (implicit val ec: ExecutionContext)
  extends TransactionsRepository
    with Tables {
  val dbConfig = dbConfigProvide.get[PostgresProfile]
  val db = dbConfig.db
  override def createSale(saleData: SaleData, deliveryPrice: Double): Future[Sale] = {
    val address = saleData.address
    val result = db.run((for {
      tr <- Transactions returning Transactions.map(_.id) into ((tr, transactionId) => tr.copy(id=Some(transactionId))) += TransactionsRow(Timestamp.from(Instant.now), saleData.storeId, None)
      sale <- Sales returning Sales.map(_.id) into((sale, saleId) => sale.copy(id=Some(saleId))) += SalesRow(saleData.deliveryMethod, deliveryPrice, tr.id.get, address.country, address.state, address.street, address.city, address.zip,address.extra, None)
      items <- Items returning Items.map(item => (item.saleId, item.productId)) into ((item, _) => item) ++= saleData.itemsData.map(itemData => ItemsRow(
        productId = itemData.productId,
        quantity = itemData.quantity,
        description = itemData.description,
        saleId = sale.id.get,
        price = itemData.price
      ))
    } yield (
      model.dao.Tables.TransactionsRow(tr.createdAt, tr.storeId, tr.id),
      model.dao.Tables.SalesRow(sale.deliveryMethod, sale.deliveryPrice, sale.transactionId, sale.addressCountry, sale.addressState, sale.addressStreet, sale.addressCity, sale.addressZip, sale.addressExtra, sale.id),
      items map (item => model.dao.Tables.ItemsRow(item.productId, item.quantity, item.description, item.saleId, item.price)))).transactionally)
    result map (tuple => {
      val (transaction, sale, items) = tuple
      Sale.from(sale, transaction, items)
    })
  }

  override def createPurchase(purchaseData: PurchaseData): Future[Purchase] = ???

  override def getTransactions(from: Instant, to: Instant): Future[Seq[Transaction]] = {
//    Transactions.join(Sales).on(_.id === _.transactionId)
//      .join(Purchases).on(_._1.id === _.transactionId)
//      .join(Items).on(_._1._2.id === _.saleId)
//    val result = db.run(Transactions.result) map(r ⇒ r)
    ???
  }

  override val profile = PostgresProfile
}
