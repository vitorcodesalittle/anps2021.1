package controllers

import domain.users.{User, UserRepositoryBDR}
import play.api.libs.json.Json

import scala.concurrent.{Await, ExecutionContext, Future}
import play.api.mvc._

import javax.inject.{Inject, Singleton}
import scala.concurrent.duration.Duration

@Singleton
class UserController @Inject() (val repo: UserRepositoryBDR, val controllerComponents: ControllerComponents)
  (implicit ec: ExecutionContext) extends BaseController {

  implicit val userWrites = Json.writes[User]

  def login(): Action[AnyContent] = Action {
    NotImplemented("Sorry")
  }

  def signUp() = Action {
    val user = User(id = None, email = "vitormaia1890@gmail.com", name = "vitor maia", passwordHash = Some("asdasdasd"), passwordSalt = Some("asdasdasd"))
    val savedUser = Await.result(repo.create(user), Duration.Inf) // @TODO: Duration.Inf ???
    Ok(Json.toJson(savedUser))
  }

  def verifyPassword(user: User, password: String): Future[Boolean] = ???
  def verifyPasswordStrength(password: String): Future[Int] = ???

}

