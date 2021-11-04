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
  val db = dbConfig.db

  override def getAllTransactions(storeId: Int): Future[Seq[(Transaction, Option[Sale], Option[Purchase], Seq[(Item, Product)])]] = {
    val p = Product(Some(1), "Um produto bem legal", 32.00, 300, "asdasdasd", storeId)
    val ts = 1 to 6 map ((i: Int) ⇒ (
      Transaction(Some(i), storeId, Instant.now()),
      if (i < 3) Some(Sale(Some(i), i, "CORREIOS", 3.43, Address("", "", "", "", ""))) else None,
      None,
      Seq((Item(Some(3), 1, 1, 33, 33.33), p), (Item(Some(3), 1, 1, 33, 33.33), p), (Item(Some(3), 1, 1, 33, 33.33), p))
    ))
    Future.successful(ts)
  }

  override def getTransactions(storeId: Int, since: Instant, to: Instant): Future[Seq[(Transaction, Option[Sale], Option[Purchase], Seq[Item])]] = ???

  override def createSale(sale: Sale): Future[Sale] = ???

  override def getSales(since: Instant, to: Instant): Future[Seq[Sale]] = ???

  override def getPurchases(since: Instant, to: Instant): Future[Seq[Purchase]] = ???

  override def createPurchase(purchase: Purchase): Future[Purchase] = ???

  override def deleteSale(saleId: Int): Future[Int] = ???

  val createSchemaFuture = for {
    _ ← db.run(DBIO.sequence(Seq(transactions.schema.create, sales.schema.create, purchases.schema.create)))
  } yield true
  Await.ready(createSchemaFuture, Duration.Inf)
}
