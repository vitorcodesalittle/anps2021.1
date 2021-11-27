package model.products

import model.transactions.forms.ItemData
import model.users.DBRunner
import play.api.db.slick.DatabaseConfigProvider
import slick.basic.DatabaseConfig
import slick.jdbc.PostgresProfile
import slick.jdbc.PostgresProfile.api._
import slick.jdbc.meta.MTable
import slick.lifted.TableQuery

import javax.inject.{Inject, Singleton}
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

@Singleton
class ProductRepositoryRDB @Inject()(dbConfigProvider: DatabaseConfigProvider, implicit val ec: ExecutionContext)
  extends DBRunner(dbConfigProvider) with ProductRepository {

  override val dbConfig: DatabaseConfig[PostgresProfile] = dbConfigProvider.get[PostgresProfile]
  override lazy val db = dbConfig.db
  val products = TableQuery[Products]

  override def decrementStock(items: Seq[ItemData]): DBIO[Unit] = {
    println("DECREMENTING", items)
    val idsAndStocks = for {
      ps <- getById(items.map(_.productId))
      stocks <- DBIO.successful((ps zip items).map((pair) => {
        val (p, i) = pair
        p.stock - i.quantity
      }))
    } yield ps map (_.id) zip stocks
    idsAndStocks.map(result => DBIO.sequence(
      result.map(pair => {
        val (Some(id), newStock) = pair
        println("Updating " + id + "  to " + newStock)
        products.filter(_.id === id).map(_.stock).update(newStock)
      })
    ))
  }

  override def getAll(): DBIO[Seq[Product]] = products.result

  override def getByStoreId(storeId: Int): DBIO[Seq[Product]] = {
    products.filter(_.storeId === storeId).result
  }

  override def create(product: Product): DBIO[Product] = {
    (products returning products.map(_.id) into ((product, newId) ⇒ product.copy(id = Some(newId)))) += product
  }

  override def getByName(name: String): DBIO[Product] = ???

  override def getById(ids: Seq[Int]): DBIO[Seq[Product]] = {
    products.filter(_.id.inSet(ids)).result
  }


  def getPrices(ids: Seq[Int]): DBIO[Seq[Double]] = {
    products.filter(_.id.inSet(ids)).map(_.suggestedPrice).result
  }

  def hasSufficientStock(items: Seq[ItemData]): DBIO[Boolean] = {
    for {
      ps <- products.filter(_.id.inSet(items.map(_.productId))).result
      valid <- DBIO.from( Future {
        (ps zip items).foldLeft(false)((acc, value) => acc && (value._1.stock >= value._2.quantity))
      })
    } yield valid
  }

  override def update(productUpdate: Product): DBIO[Product] = ???

  override def remove(id: Int): DBIO[Int] = ???

  val createSchemaFuture = for {
    tables ← db.run {
      MTable.getTables
    }
    if !tables.map(_.name.name).contains(products.baseTableRow.tableName)
    _ ← db.run(DBIO.sequence(Seq(products.schema.create)))
  } yield true
  Await.ready(createSchemaFuture, Duration.Inf)

}
