package model.services.session

import play.api.mvc.Results.Forbidden
import play.api.mvc._

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class UserAction @Inject()(val parser: BodyParsers.Default, sessionService: SessionService)(implicit val executionContext: ExecutionContext)
  extends ActionBuilder[UserRequest, AnyContent] {
  override def invokeBlock[A](request: Request[A], block: UserRequest[A] => Future[Result]): Future[Result] = {
    val cookieOption = request.cookies.get(sessionService.AUTH_TOKEN)
    val userInfoOption: Option[UserInfo] = for {
      cookie <- cookieOption
      validCookie <- validateCookie(cookie)
      decodedCookie <- sessionService.decodeCookie(validCookie.value)
    } yield decodedCookie
    userInfoOption match {
      case Some(userInfo) => block(UserRequest(request, userInfo))
      case None => Future(Forbidden)
    }
  }

  private def validateCookie(cookie: Cookie): Option[Cookie] = {
    val validMaxAge = cookie.maxAge match {
      case Some(maxAge) => maxAge > 0
      case None => true
    }
    val valid = validMaxAge // add other validations as needed
    if (valid) {
      Some(cookie)
    } else {
      None
    }
  }

}
