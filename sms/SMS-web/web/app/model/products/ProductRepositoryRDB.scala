package model.products

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
class ProductRepositoryRDB @Inject()(dbConfigProvider: DatabaseConfigProvider, implicit val ec: ExecutionContext) extends ProductRepository {
  val dbConfig: DatabaseConfig[PostgresProfile] = dbConfigProvider.get[PostgresProfile]
  val db = dbConfig.db
  val products = TableQuery[Products]

  override def getAll(): Future[Seq[Product]] = db run products.result

  override def getByStoreId(storeId: Int): Future[Seq[Product]] = db run {
    products.filter(_.storeId === storeId).result
  }

  override def create(product: Product): Future[Product] = db run {
    (products returning products.map(_.id) into ((product, newId) ⇒ product.copy(id = Some(newId)))) += product
  }

  override def getByName(name: String): Future[Product] = ???

  override def getById(id: Int): Future[Product] = ???


  override def update(productUpdate: Product): Future[Product] = ???

  override def remove(id: Int): Future[Int] = ???

  val createSchemaFuture = for {
    tables ← db.run {
      MTable.getTables
    }
    if !tables.map(_.name.name).contains(products.baseTableRow.tableName)
    _ ← db.run(DBIO.sequence(Seq(products.schema.create)))
  } yield true
  Await.ready(createSchemaFuture, Duration.Inf)

}
