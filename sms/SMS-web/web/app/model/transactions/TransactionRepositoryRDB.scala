package model.transactions

import model.products.{Product, Products}
import play.api.db.slick.DatabaseConfigProvider
import slick.dbio.DBIO
import slick.jdbc.PostgresProfile
import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery

import java.time.Instant
import javax.inject.{Inject, Singleton}
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.util.{Failure, Success}

@Singleton
class TransactionRepositoryRDB @Inject()(dbConfigProvider: DatabaseConfigProvider, implicit val ec: ExecutionContext) extends TransactionRepository {
  val dbConfig = dbConfigProvider.get[PostgresProfile]
  val transactions = TableQuery[Transactions]
  val sales = TableQuery[Sales]
  val purchases = TableQuery[Purchases]
  val allItems = TableQuery[Items]
  val products = TableQuery[Products]
  val db = dbConfig.db

  override def getAllTransactions(storeId: Int): Future[Seq[(Transaction, Option[Sale], Option[Purchase], Seq[(Item, Product)])]] = {
    // provavelmente existe um jeito mais limpo de fazer isso
    val ts = db.run((for {
      ((t, s), p) ← transactions joinLeft sales on (_.id === _.transactionId) joinLeft purchases on ((ts, p) ⇒ {
        val (t, s) = ts
        t.id === p.transactionId
      })
    } yield (t, s, p)).result)
    ts map (tss ⇒ {
      tss map (tt ⇒ {
        val (t, s, p) = tt
        val f = db.run((allItems.filter(_.transactionId === t.id) join products on (_.productId === _.id)).map(pair ⇒ (pair._1, pair._2)).result)
        val ttt = Await.ready(f, Duration.Inf).value.get match {
          case Success(items) ⇒ {
            Future.successful((t, s, p, items))
          }
          case Failure(e) ⇒ Future.failed(e)
        }
        ttt.value.get.get
      })
    })
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

  Await.ready(db.run(DBIO.sequence(Seq(purchases.schema.create, allItems.schema.create, transactions.schema.create, sales.schema.create))), Duration.Inf)
}
