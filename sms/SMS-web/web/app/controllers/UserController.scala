package controllers

import domain.users.{User, UserRepositoryBDR}

import scala.concurrent.{Await, ExecutionContext, Future}
import play.api.mvc._

import javax.inject.{Inject, Singleton}
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}

@Singleton
class UserController @Inject() (val repo: UserRepositoryBDR, val controllerComponents: ControllerComponents)
  (implicit ec: ExecutionContext) extends BaseController {

  def login() = Action {
    val user = User(id = None, email = "vitormaia1890@gmail.com", name = "vitor maia", passwordHash = Some("asdasdasd"), passwordSalt = Some("asdasdasd"))
    println(f"Let's try to insert $user")
    val savedUser = Await.result(repo.create(user), Duration.Inf)
    Ok(f"$savedUser")
  }

  def signUp() = Action {
    Ok("ok")
  }

  def verifyPassword(user: User, password: String): Future[Boolean] = ???
  def verifyPasswordStrength(password: String): Future[Int] = ???

}

