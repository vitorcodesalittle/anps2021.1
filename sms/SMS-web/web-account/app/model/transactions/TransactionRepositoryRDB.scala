package model.transactions

import model.products.{Product, Products}
import util.DBRunner

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext}
import java.time.Instant

import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider
import slick.dbio.DBIO
import slick.jdbc.PostgresProfile
import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery

@Singleton
class TransactionRepositoryRDB @Inject()(dbConfigProvider: DatabaseConfigProvider, implicit val ec: ExecutionContext)
  extends DBRunner(dbConfigProvider) with TransactionRepository {
  override val dbConfig = dbConfigProvider.get[PostgresProfile]
  override lazy val db = dbConfig.db
  val sales = TableQuery[Sales]
  val purchases = TableQuery[Purchases]
  val allItems = TableQuery[Items]
  val products = TableQuery[Products]

  println("Creating transactions")
  Await.result(db.run(DBIO.sequence(Seq(purchases.schema.createIfNotExists,  sales.schema.createIfNotExists, allItems.schema.createIfNotExists))), Duration.Inf)

  private def mountSales(sales: Seq[(Sale, Item, Product)]): Seq[Sale] = {
    sales.foldLeft(Map[Int, Sale]())((acc, tuple) => {
      val (sale, item, product) = tuple
      val itemWithProduct = item.copy(product = Some(product))
      if (acc contains sale.id.get) {
        val updated = sale.copy(items = Some(sale.items.get :+ itemWithProduct))
        acc + (sale.id.get -> updated)
      } else {
        acc + (sale.id.get -> sale.copy(items=Some(Seq(itemWithProduct))))
      }
    }).values.toSeq
  }

  def getAllSales(storeId: Int): DBIO[Seq[Sale]] = {
    (for {
      ss <- sales.filter(_.storeId === storeId)
      its <- allItems if (ss.transactionId === its.transactionId)
      ps <- products if (its.productId === ps.id)
    } yield (ss, its, ps)).result.map(mountSales)
  }

  override def getTransactions(storeId: Int, since: Instant, to: Instant): DBIO[Seq[Sale]] = ???

  override def createSale(sale: Sale): DBIO[Sale] = {
    for {
      sale <- sales returning sales.map(sale => (sale.id, sale.transactionId)) into ((sale, pair) => sale.copy(id = Some(pair._1), transactionId = Some(pair._2))) += sale
      items <- allItems returning allItems.map(_.id) into ((item, itemId) => item.copy(id = Some(itemId))) ++= (sale.items.get.map(item => item.copy(transactionId = sale.transactionId)))
    } yield sale.copy(items = Some(items))
  }

  override def createPurchase(purchase: Purchase): DBIO[Purchase] = ???

  override def deleteTransaction(transactionId: Int): DBIO[Int] = ???

  override def getAllTransactions(storeId: Int): DBIO[Seq[Sale]] = ???

  override def getAllPurchases(storeId: Int): DBIO[Seq[Purchase]] = ???
}
