package model.users

import slick.dbio.DBIO

import scala.concurrent.Future

trait UserRepository {
  def create(user: User): DBIO[User]

  def getByEmail(email: String): DBIO[User]
}