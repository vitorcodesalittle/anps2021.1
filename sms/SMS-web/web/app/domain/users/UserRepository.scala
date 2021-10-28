package domain.users

import util.Repository

import scala.concurrent.Future

trait UserRepository extends Repository[User] {
  def getByEmail(email: String): Future[User]
}