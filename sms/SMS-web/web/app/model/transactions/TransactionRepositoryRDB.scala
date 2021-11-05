package model.transactions

import model.global.Address
import model.products.Product
import play.api.db.slick.DatabaseConfigProvider
import slick.dbio.DBIO
import slick.jdbc.PostgresProfile
import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery

import java.time.Instant
import javax.inject.{Inject, Singleton}
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

@Singleton
class TransactionRepositoryRDB @Inject()(dbConfigProvider: DatabaseConfigProvider, implicit val ec: ExecutionContext) extends TransactionRepository {
  val dbConfig = dbConfigProvider.get[PostgresProfile]
  val transactions = TableQuery[Transactions]
  val sales = TableQuery[Sales]
  val purchases = TableQuery[Purchases]
  val allItems = TableQuery[Items]
  val db = dbConfig.db

  override def getAllTransactions(storeId: Int): Future[Seq[(Transaction, Option[Sale], Option[Purchase], Seq[(Item, Product)])]] = {
    val p = Product(Some(1), "Um produto bem legal", 32.00, 300, "asdasdasd", storeId)
    val ts = 1 to 6 map ((i: Int) ⇒ (
      Transaction(Some(i), storeId, Instant.now()),
      if (i < 3) Some(Sale(Some(i), Some(i), "CORREIOS", 3.43, Address("", "", "", "", ""))) else None,
      None,
      Seq((Item(Some(3), Some(1), 1, 33, 33.33), p), (Item(Some(3), Some(1), 1, 33, 33.33), p), (Item(Some(3), Some(1), 1, 33, 33.33), p))
    ))
    Future.successful(ts)
  }

  override def getTransactions(storeId: Int, since: Instant, to: Instant): Future[Seq[(Transaction, Option[Sale], Option[Purchase], Seq[Item])]] = ???

  override def createSale(sale: Sale): Future[Sale] = ???

  override def getSales(since: Instant, to: Instant): Future[Seq[Sale]] = ???

  override def getPurchases(since: Instant, to: Instant): Future[Seq[Purchase]] = ???

  override def createPurchase(purchase: Purchase): Future[Purchase] = ???

  override def deleteSale(saleId: Int): Future[Int] = ???

  override def createSaleWithTransaction(transaction: Transaction, sale: Sale, items: Seq[Item]): Future[(Transaction, Sale, Seq[Item])] = {
    db.run((for {
      tr ← ((transactions returning transactions.map(_.id) into ((tr, id) ⇒ tr.copy(id = Some(id)))) += transaction)
      its ← ((allItems returning allItems.map(_.id) into ((it, id) ⇒ it.copy(id = Some(id)))) ++= items.map(it ⇒ it.copy(transactionId = tr.id)))
      s ← ((sales returning sales.map(_.id) into ((s, id) ⇒ s.copy(id = Some(id)))) += sale.copy(transactionId = tr.id))
    } yield (tr, s, its)).transactionally)
  }


  val createSchemaFuture = for {
    _ ← db.run(DBIO.sequence(Seq(allItems.schema.create, transactions.schema.create, sales.schema.create, purchases.schema.create)))
  } yield true
  Await.ready(createSchemaFuture, Duration.Inf)
}
