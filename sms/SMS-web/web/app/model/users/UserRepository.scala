package model.users

import scala.concurrent.Future

trait UserRepository {
  def create(user: User, password: String): Future[User]

  def getByEmail(email: String): Future[User]
}