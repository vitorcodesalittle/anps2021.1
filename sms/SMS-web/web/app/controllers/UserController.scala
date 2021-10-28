package controllers

import domain.users.forms.{LoginData, SignUpData}
import domain.users.{UserRepositoryBDR, User}
import play.api.data.Form
import play.api.data.Forms.{mapping, text}
import play.api.libs.json._
import play.api.mvc._

import javax.inject.{Inject, Singleton}
import scala.concurrent.duration.Duration
import scala.concurrent.{ExecutionContext, Await}
import scala.util.{Success, Failure}

trait PostRequestHeader extends MessagesRequestHeader with PreferredMessagesProvider

@Singleton
class UserController @Inject()(repo: UserRepositoryBDR, val controllerComponents: ControllerComponents)
                              (implicit ec: ExecutionContext) extends BaseController with play.api.i18n.I18nSupport {
  implicit val userWrites: OWrites[User] = Json.writes[User]

  val signUpForm: Form[SignUpData] = Form(
    mapping(
      "name" -> text(minLength = 1),
      "email" -> text(minLength = 1),
      "password" -> text,
    )(SignUpData.apply)(SignUpData.unapply)
  )

  val loginForm: Form[LoginData] = Form(
    mapping(
      "email" -> text(minLength = 1),
      "password" -> text
    )(LoginData.apply)(LoginData.unapply)
  )

  def index: Action[AnyContent] = Action { implicit request =>
    Ok(views.html.user(signUpForm, loginForm))
  }

  def login(): Action[AnyContent] = Action { implicit request => {
    val loginData = loginForm.bindFromRequest.get
    val userFuture = repo.getByEmail(loginData.email).filter(verifyPassword(_, loginData.password))
    val maybeUser = Await.ready(userFuture, Duration.Inf).value.get
    Ok(maybeUser match {
      case Success(u) => Json.toJson(u) // @TODO: proper redirect
      case Failure(_) => Json.toJson(Map("message" -> "Invalid username or password"))
    }).as("application/json")
  }
  }

  def signUp(): Action[AnyContent] = Action { implicit request => {
    val signUpData = signUpForm.bindFromRequest.get
    val passwordStrength = verifyPasswordStrength(signUpData.password)
    if (passwordStrength < 6) {
      BadRequest(Json.toJson(Map("message" -> "Password is too weak")))
    } else {
      // @TODO: validation
      val user = User(id = None, email = signUpData.email, name = signUpData.name, passwordHash = Some(hashPassword(signUpData.password)), passwordSalt = Some(genSalt()))
      val savedUser = Await.result(repo.create(user), Duration.Inf) // @TODO: Duration.Inf ???
      Ok(Json.toJson(savedUser)).as("application/json")
    }
  }
  }

  def hashPassword(str: String): String = str // @TODO

  def genSalt(): String = "" // @TODO

  def verifyPasswordStrength(password: String): Int = password.length

  def verifyPassword(user: User, password: String): Boolean = user.passwordHash == password

}

