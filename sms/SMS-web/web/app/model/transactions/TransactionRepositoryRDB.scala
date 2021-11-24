package model.transactions

import model.products.{Products}
import model.users.DBRunner
import play.api.db.slick.DatabaseConfigProvider
import slick.dbio.DBIO
import slick.jdbc.PostgresProfile
import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery

import java.time.Instant
import javax.inject.{Inject, Singleton}
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext}

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

  override def getAllTransactions(storeId: Int): DBIO[Seq[Transaction]] = ???

  override def getTransactions(storeId: Int, since: Instant, to: Instant): DBIO[Seq[Transaction]] = ???

  override def createSale(sale: Sale): DBIO[Sale] = ???

  override def createPurchase(purchase: Purchase): DBIO[Purchase] = ???

  override def deleteTransaction(transactionId: Int): DBIO[Int] = ???
}
