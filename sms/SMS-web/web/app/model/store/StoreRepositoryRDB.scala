package model.store

import play.api.db.slick.DatabaseConfigProvider
import slick.basic.DatabaseConfig
import slick.jdbc.PostgresProfile
import slick.jdbc.PostgresProfile.api._
import slick.jdbc.meta.MTable
import slick.lifted.TableQuery

import javax.inject.Inject
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

class StoreRepositoryRDB @Inject()(implicit dbConfigProvider: DatabaseConfigProvider, implicit val ec: ExecutionContext) extends StoreRepository {

  val dbConfig: DatabaseConfig[PostgresProfile] = dbConfigProvider.get[PostgresProfile]
  val db = dbConfig.db

  val stores = TableQuery[Stores]

  override def create(store: Store): Future[Store] = db.run {
    (stores returning stores.map(_.id) into ((s, newId) ⇒ s.copy(id = Some(newId)))) += store
  }

  override def getFromOwner(ownerId: Int): Future[Seq[Store]] = db.run {
    stores.filter(_.ownerId === ownerId).result
  }

  val createSchemaFuture = for {
    tables ← db.run(MTable.getTables)
    if !tables.map(_.name.name).contains(stores.baseTableRow.tableName)
    _ ← db.run(DBIO.sequence(Seq(stores.schema.create)))
  } yield true
  Await.ready(createSchemaFuture, Duration.Inf)
}
