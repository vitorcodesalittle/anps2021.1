package controllers

import model.Facade
import model.services.session.UserAction
import model.store.forms.StoreData
import model.users.User
import model.users.forms.{LoginData, SignUpData}
import play.api.data.Form
import play.api.data.Forms.{mapping, of, text}
import play.api.libs.json._
import play.api.mvc._

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success, Try}

@Singleton
class UserController @Inject()(val controllerComponents: ControllerComponents, boundary: Facade, userAction: UserAction)
                              (implicit ec: ExecutionContext)
  extends BaseController {
  def login(): Action[JsValue] = Action(parse.json) {
    implicit request => {
      request.body.validate[LoginData] match {
        case JsSuccess(loginData, _) ⇒ {
          val userCookieTry: Try[(User, Cookie)] = boundary.login(loginData)
          userCookieTry match {
            case Success((user, authCookie)) => {
              Ok(Json.toJson(user)).withCookies(authCookie)
            }
            case Failure(exception) => {
              println(exception)
              InternalServerError("Could not login")
            }
          }
        }
        case JsError(error) ⇒ {
          BadRequest("Invalid Json")
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
          BadRequest(Json.obj("message" -> "Essa senha é inválida por algum motivo"))
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

