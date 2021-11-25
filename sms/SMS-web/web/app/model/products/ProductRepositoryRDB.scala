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
  def hasSufficientStock(items: Seq[ItemData]): DBIO[Boolean] = ???

  def getPrices(ids: Seq[Int]): DBIO[Seq[Double]] = ???

  override val dbConfig: DatabaseConfig[PostgresProfile] = dbConfigProvider.get[PostgresProfile]
  override lazy val db = dbConfig.db
  val products = TableQuery[Products]

  override def getAll(): DBIO[Seq[Product]] = products.result

  override def getByStoreId(storeId: Int): DBIO[Seq[Product]] = {
    products.filter(_.storeId === storeId).result
  }

  override def create(product: Product): DBIO[Product] = {
    (products returning products.map(_.id) into ((product, newId) ⇒ product.copy(id = Some(newId)))) += product
  }

  override def getByName(name: String): DBIO[Product] = ???

  override def getById(id: Int): DBIO[Product] = ???


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
