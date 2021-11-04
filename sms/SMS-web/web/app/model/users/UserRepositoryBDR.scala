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

@Singleton
class UserRepositoryBDR @Inject()(encryptionService: EncryptionService, dbConfigProvider: DatabaseConfigProvider, implicit val ec: ExecutionContext) extends UserRepository {
  val dbConfig: DatabaseConfig[PostgresProfile] = dbConfigProvider.get[PostgresProfile]
  val db = dbConfig.db
  val users = TableQuery[Users]

  def getAll(): Future[Seq[User]] = db run users.result

  override def create(user: User, password: String): Future[User] = {
    encryptionService.hashPassword(password, encryptionService.genSalt) match {
      case Success(hashedPassword) ⇒ db.run {
        val userWithPassword = user.copy(passwordHash = Some(hashedPassword))
        (users returning users.map(_.id) into ((_, newId) => user.copy(id = Some(newId)))) += userWithPassword
      }
      case Failure(e) ⇒ Future.failed(e)
    }
  }

  override def getByEmail(email: String): Future[User] = db.run {
    users.filter(user => user.email === email).result.head
  }

  val createSchemaFuture = for {
    tables ← db.run {
      MTable.getTables
    }
    if !tables.map(_.name.name).contains(users.baseTableRow.tableName)
    _ ← db.run(DBIO.sequence(Seq(users.schema.create)))
  } yield true
  Await.ready(createSchemaFuture, Duration.Inf)
}
