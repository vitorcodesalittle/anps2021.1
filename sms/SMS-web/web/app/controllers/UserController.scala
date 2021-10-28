package controllers

import domain.users.forms.SignUpData
import domain.users.{User, UserRepositoryBDR}
import play.api.data.Form
import play.api.data.Forms.{mapping, text}
import play.api.libs.json._
import play.api.mvc._

import javax.inject.{Inject, Singleton}
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

trait PostRequestHeader extends MessagesRequestHeader with PreferredMessagesProvider

@Singleton
class UserController @Inject()(repo: UserRepositoryBDR, val controllerComponents: ControllerComponents)
                              (implicit ec: ExecutionContext) extends BaseController with play.api.i18n.I18nSupport {
  implicit val userWrites: OWrites[User] = Json.writes[User]

  val userForm: Form[SignUpData] = Form(
    mapping(
      "name" -> text(minLength = 1),
      "email" -> text(minLength = 1),
      "password" -> text,
    )(SignUpData.apply)(SignUpData.unapply)
  )

  def index: Action[AnyContent] = Action { implicit request =>
    Ok(views.html.user(userForm))
  }

  def login(): Action[AnyContent] = Action {
    NotImplemented("Sorry")
  }

  def signUp(): Action[AnyContent] = Action { implicit request => {
    val userData = userForm.bindFromRequest.get
    val passwordStrength = verifyPasswordStrength(userData.password)
    if (passwordStrength < 6) {
      BadRequest(Json.toJson(Map("message" -> "Password is too weak")))
    } else {
      // @TODO: validation
      val user = User(id = None, email = userData.email, name = userData.name, passwordHash = Some(hashPassword(userData.password)), passwordSalt = Some(genSalt()))
      val savedUser = Await.result(repo.create(user), Duration.Inf) // @TODO: Duration.Inf ???
      Ok(Json.toJson(savedUser)).as("application/json")
    }
  }
  }

  def hashPassword(str: String): String = str // @TODO

  def genSalt(): String = "" // @TODO

  def verifyPasswordStrength(password: String): Int = password.length

  def verifyPassword(user: User, password: String): Future[Boolean] = ???

}

