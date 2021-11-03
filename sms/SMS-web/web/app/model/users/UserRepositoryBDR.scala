package model.users

import model.services.encryption.EncryptionService
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import slick.jdbc.meta.MTable

import javax.inject.{Inject, Singleton}
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.util.{Failure, Success}

@Singleton
class UserRepositoryBDR @Inject()(encryptionService: EncryptionService, dbConfigProvider: DatabaseConfigProvider, implicit val ec: ExecutionContext) extends UserRepository {
  protected val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  class Users(tag: Tag) extends Table[User](tag, "USERS") {
    def * = (id.?, name, email, passwordHash.?, emailVerified.?) <> (User.tupled, User.unapply)

    def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)

    def name = column[String]("NAME", O.Length(256))

    def email = column[String]("EMAIL", O.Length(256))

    def emailVerified = column[Boolean]("EMAIL_VERIFIED", O.Default(true))

    private def passwordHash = column[String]("PASSWORD_HASH")

    private def passwordSalt = column[String]("PASSWORD_SALT")
  }

  val users = TableQuery[Users]

  val existing = db.run(MTable.getTables)
  val f = existing.flatMap(v => {
    val names = v.map(mt => mt.name.name)
    val createIfNotExist = List(users).filter(table =>
      (!names.contains(table.baseTableRow.tableName))).map(_.schema.create)
    db.run(DBIO.sequence(createIfNotExist))
  })

  def getAll(): Future[Seq[User]] = db run users.result

  override def create(user: User, password: String): Future[User] = {
    encryptionService.hashPassword(password, encryptionService.genSalt) match {
      case Success(hashedPassword) ⇒ db.run {
        val userWithPassword = user.copy(passwordHash = Some(hashedPassword))
        (users returning users.map(_.id) into ((_, newId) => user.copy(id = Some(newId)))) += userWithPassword
      }
      case Failure(other) ⇒ throw new RuntimeException("Failed to hash password")
    }
  }

  Await.result(f, Duration.Inf)

  override def getByEmail(email: String): Future[User] = db.run {
    users.filter(user => user.email === email).result.head
  }


}
