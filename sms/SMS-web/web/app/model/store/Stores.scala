package model.store

import model.users.Users
import slick.jdbc.PostgresProfile.api._

class Stores(tag: Tag) extends Table[Store](tag, "STORES") {
  def id: Rep[Int] = column[Int]("ID", O.PrimaryKey, O.AutoInc)

  def name: Rep[String] = column[String]("NAME", O.Length(256))

  def ownerId: Rep[Int] = column[Int]("OWNER_ID")

  def owner = foreignKey("OWNER", ownerId, users)(_.id)

  override def * = (id.?, name, ownerId) <> (Store.tupled, Store.unapply)

  lazy val users = TableQuery[Users]
}
