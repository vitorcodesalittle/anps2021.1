package model.users

import model.services.encryption.EncryptionService
import play.api.db.slick.DatabaseConfigProvider
import slick.basic.DatabaseConfig
import slick.jdbc.PostgresProfile
import slick.jdbc.PostgresProfile.api._
import slick.jdbc.meta.MTable
import slick.lifted.TableQuery

import javax.inject.{Inject, Singleton}
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.util.{Failure, Success}

abstract class DBRunner {
  val dbConfigProvider: DatabaseConfigProvider
  val dbConfig = dbConfigProvider.get[PostgresProfile]
  val db = dbConfig.db
  def run[R, S <: NoStream, T <: Effect](action: DBIOAction[R, S, T]) = {
    db.run(action)
  }
}

@Singleton
class UserRepositoryBDR @Inject()(override val dbConfigProvider: DatabaseConfigProvider, implicit val ec: ExecutionContext) extends DBRunner with UserRepository {
  override val dbConfig: DatabaseConfig[PostgresProfile] = dbConfigProvider.get[PostgresProfile]
  override val db = dbConfig.db
  val users = TableQuery[Users]

  def getAll: Future[Seq[User]] = db run users.result

  override def create(user: User): DBIO[User] = {
    (users returning users.map(_.id) into ((_, newId) => user.copy(id = Some(newId)))) += user
  }

  override def getByEmail(email: String): DBIO[User] = {
    users.filter(user => user.email === email).result.head
  }

  val createSchemaFuture: Future[Boolean] = for {
    tables ← db.run {
      MTable.getTables
    }
    if !tables.map(_.name.name).contains(users.baseTableRow.tableName)
    _ ← db.run(DBIO.sequence(Seq(users.schema.create)))
  } yield true
  Await.ready(createSchemaFuture, Duration.Inf)
}
