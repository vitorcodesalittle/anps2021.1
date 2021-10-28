package domain.users

import scala.concurrent.Future

trait UserRepository {
  def create(user: User): Future[User]

  def getByEmail(email: String): Future[User]
}