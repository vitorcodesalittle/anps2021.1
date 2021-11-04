package model.users

import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag

class Users(tag: Tag) extends Table[User](tag, "USERS") {
  def * = (id.?, name, email, passwordHash.?, emailVerified.?) <> (User.tupled, User.unapply)

  def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)

  def name = column[String]("NAME", O.Length(256))

  def email = column[String]("EMAIL", O.Length(256), O.Unique)

  def emailVerified = column[Boolean]("EMAIL_VERIFIED", O.Default(true))

  private def passwordHash = column[String]("PASSWORD_HASH")
}
