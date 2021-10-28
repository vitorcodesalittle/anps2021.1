package domain.users

import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import slick.jdbc.meta.MTable

import javax.inject.{Inject, Singleton}
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

@Singleton
class UserRepositoryBDR @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) extends UserRepository {
   protected val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  class Users(tag: Tag) extends Table[User](tag, "USERS") {
    def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)
    def name = column[String]("NAME", O.Length(256))
    def email = column[String]("EMAIL", O.Length(256))
    private def passwordHash = column[String]("PASSWORD_HASH")
    private def passwordSalt = column[String]("PASSWORD_SALT")
    def emailVerified = column[Boolean]("EMAIL_VERIFIED", O.Default(true))
    def * = (id.?, name, email, passwordHash.?, passwordSalt.?, emailVerified.?) <> (User.tupled, User.unapply)
  }

  val users = TableQuery[Users]

  // @TODO: This code for every repo seens weird.
  val existing = db.run(MTable.getTables)
  val f = existing.flatMap( v => {
    val names = v.map(mt => mt.name.name)
    val createIfNotExist = List(users).filter( table =>
      (!names.contains(table.baseTableRow.tableName))).map(_.schema.create)
    db.run(DBIO.sequence(createIfNotExist))
  })
  Await.result(f, Duration.Inf)

  def getAll(): Future[Seq[User]] = db run users.result

  override def create(user: User): Future[User] = db.run {
    (users returning users.map(_.id) into ((_, newId) => user.copy(id = Some(newId))) ) += user
  }

  override def exists(thing: User): Future[Boolean] = ???

  override def update(thing: User): Future[User] = ???

  override def remove(thing: User): Future[Int] = ???

  override def getByEmail(email: String): Future[User] = db.run {
    users.filter(user => user.email === email).result.head
  }
}
