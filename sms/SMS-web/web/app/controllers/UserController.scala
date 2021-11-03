package controllers

import domain.users.forms.{LoginData, SignUpData}
import domain.users.{User, UserJson, UserRepositoryBDR}
import play.api.data.Form
import play.api.data.Forms.{mapping, text}
import play.api.libs.json._
import play.api.mvc._
import services.encryption.EncryptionService
import services.session.{SessionService, UserAction, UserInfo}

import javax.inject.{Inject, Singleton}
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext}
import scala.util.{Failure, Success, Try}

@Singleton
class UserController @Inject()(repo: UserRepositoryBDR, val controllerComponents: ControllerComponents, userAction: UserAction, sessionService: SessionService, encryptionService: EncryptionService)
                              (implicit ec: ExecutionContext) extends BaseController with play.api.i18n.I18nSupport with UserJson {

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

  def login(): Action[AnyContent] = Action {
    implicit request => {
      val loginData = loginForm.bindFromRequest().get
      val userFuture = repo getByEmail loginData.email
      val userTry = for {
        u <- Await.ready(userFuture, Duration.Inf).value.get
        validUser <- verifyPassword(u, loginData.password)
        if validUser
      } yield u
      userTry match {
        case Success(User(Some(id), _, _, _, _, _)) => {
          val authCookie = sessionService.encodeUserSession(UserInfo(id))
          println("Sending cookie ", authCookie)
          Redirect("/testauth").withCookies(authCookie)
        }
        case Success(_) => {
          InternalServerError("Could not get user")
        }
        case Failure(e) => {
          println(e)
          InternalServerError("Try again later")
        }
      }
    }
  }

  def verifyPassword(user: User, password: String): Try[Boolean] = user.passwordHash match {
    case Some(passwordHash) => encryptionService.verify(password, passwordHash)
    case None => Failure(new Exception("User não tem hash de senha registrada"))
  }

  def signUp(): Action[AnyContent] = Action {
    implicit request => {
      val signUpData = signUpForm.bindFromRequest.get
      val passwordStrength = verifyPasswordStrength(signUpData.password)
      if (passwordStrength < 6) {
        BadRequest(Json.toJson(Map("message" -> "Password is too weak")))
      } else {
        // @TODO: validation
        val salt = genSalt()
        hashPassword(signUpData.password, salt) match {
          case Success(hash) => {
            val user = User(id = None, email = signUpData.email, name = signUpData.name, passwordHash = Some(hash), passwordSalt = Some(salt))
            val savedUser = Await.result(repo.create(user), Duration.Inf) // @TODO: Duration.Inf ???
            Ok(Json.toJson(savedUser)).as("application/json")
          }
          case Failure(e) => {
            println(e)
            BadRequest(Json.toJson(Map("message" -> "Essa senha é inválida por algum motivo")))
          }
        }

      }
    }
  }

  def hashPassword(str: String, salt: String): Try[String] = encryptionService.hashPassword(str, salt)

  def genSalt(): String = encryptionService.genSalt

  def verifyPasswordStrength(password: String): Int = password.length // @TODO

  def testAuth(): Action[AnyContent] = userAction {
    implicit request => {
      Ok("You are authenticated!")
    }
  }

}

