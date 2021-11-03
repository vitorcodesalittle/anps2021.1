package controllers

import model.Boundary
import model.services.session.UserAction
import model.users.forms.{LoginData, SignUpData}
import model.users.{User, UserJson}
import play.api.data.Form
import play.api.data.Forms.{mapping, text}
import play.api.libs.json._
import play.api.mvc._

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success, Try}

@Singleton
class UserController @Inject()(val controllerComponents: ControllerComponents, boundary: Boundary, userAction: UserAction)
                              (implicit ec: ExecutionContext)
  extends BaseController
    with play.api.i18n.I18nSupport
    with UserJson {

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
      val loginData: LoginData = loginForm.bindFromRequest().get
      val userCookieTry: Try[(User, Cookie)] = boundary.login(loginData)
      userCookieTry match {
        case Success((_, authCookie)) => {
          Redirect("/testauth").withCookies(authCookie)
        }
        case Failure(exception) => {
          println(exception)
          InternalServerError("Could not login")
        }
      }
    }
  }

  def signUp(): Action[AnyContent] = Action {
    implicit request => {
      val signUpData = signUpForm.bindFromRequest.get
      val savedUser: Try[User] = boundary.signUp(signUpData)
      savedUser match {
        case Success(_) => {
          Redirect("/auth")
        }
        case Failure(e) => {
          println(e)
          BadRequest(Json.toJson(Map("message" -> "Essa senha é inválida por algum motivo")))
        }
      }
    }
  }

  def testAuth(): Action[AnyContent] = userAction {
    implicit request => {
      Ok("You are authenticated!")
    }
  }
}

