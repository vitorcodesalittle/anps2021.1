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
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

@Singleton
class UserController @Inject()(val controllerComponents: ControllerComponents, boundary: Facade, userAction: UserAction)
                              (implicit ec: ExecutionContext)
  extends BaseController {
  def login(): Action[JsValue] = Action(parse.json).async {
    implicit request => {
      request.body.validate[LoginData] match {
        case JsSuccess(loginData, _) ⇒ {
          boundary.login(loginData).map(userAndCookie => {
            val (user, cookie) = userAndCookie
            Ok(Json.toJson(user)).withCookies(cookie)
          }).recoverWith(error => {
            println(error)
            Future.successful(InternalServerError("Failuuu"))
          })
        }
      }

    }
  }

  def signUp(): Action[JsValue] = Action(parse.json).async {
    implicit request => {
      println(request.body.toString)
      request.body.validate[SignUpData] match{
        case JsSuccess(data, _) => {
          boundary.signUp(data).map(user => {
            Ok(Json.toJson(user))
          })
        }
        case JsError(errors) => {
          println(errors)
          Future.successful(InternalServerError("Brokennn"))
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

