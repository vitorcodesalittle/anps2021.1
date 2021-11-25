package model.transactions

import model.products.{Product, Products}
import model.users.DBRunner
import play.api.db.slick.DatabaseConfigProvider
import slick.dbio.{DBIO, Effect}
import slick.jdbc
import slick.jdbc.PostgresProfile
import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery
import slick.sql.FixedSqlStreamingAction

import java.time.Instant
import javax.inject.{Inject, Singleton}
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

@Singleton
class TransactionRepositoryRDB @Inject()(override val dbConfigProvider: DatabaseConfigProvider, implicit val ec: ExecutionContext)
  extends DBRunner with TransactionRepository {
  override val dbConfig = dbConfigProvider.get[PostgresProfile]
  override val db = dbConfig.db
  val transactions = TableQuery[Transactions]
  val sales = TableQuery[Sales]
  val purchases = TableQuery[Purchases]
  val allItems = TableQuery[Items]
  val products = TableQuery[Products]

  Await.ready(db.run(DBIO.sequence(Seq(purchases.schema.create, allItems.schema.create, transactions.schema.create, sales.schema.create))), Duration.Inf)

  def mountSales(sales: Seq[(Sale, Item, Product)]): Seq[Sale] = ???

  def getAllTransactionsGood(storeId: Int): DBIO[Seq[Sale]] = {
    (for {
      ss <- sales .filter(_.storeId === storeId)
      items <- allItems if (ss.transactionId === items.transactionId)
      products <- products if (items.productId === products.id)
    } yield (ss, items, products)).result.map(mountSales)
  }

  val stuff: Future[Seq[(TransactionId, _root_.slick.jdbc.PostgresProfile.api.Query[(Sales, (Items, Products)), (Sale, (Item, Product)), Seq])]] = db.run(getAllTransactionsGood(1))
  val ahaha = stuff map (data => {
    val d = data.map(d => {
      (d._1, d._2.)
    })
  })


  override def getTransactions(storeId: Int, since: Instant, to: Instant): DBIO[Seq[Transaction]] = ???

  override def createSale(sale: Sale): DBIO[Sale] = ???

  override def createPurchase(purchase: Purchase): DBIO[Purchase] = ???

  override def deleteTransaction(transactionId: Int): DBIO[Int] = ???
}
