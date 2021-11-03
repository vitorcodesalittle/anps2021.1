package services.session

import play.api.mvc.Results.Redirect
import play.api.mvc._

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class UserAction @Inject()(val parser: BodyParsers.Default, sessionService: SessionService)(implicit val executionContext: ExecutionContext)
  extends ActionBuilder[UserRequest, AnyContent] {
  override def invokeBlock[A](request: Request[A], block: UserRequest[A] => Future[Result]): Future[Result] = {
    println("Validating!")
    val cookieOption = request.cookies.get(sessionService.AUTH_TOKEN)
    println(cookieOption)
    val userInfoOption: Option[UserInfo] = for {
      cookie <- cookieOption
      validCookie <- validateCookie(cookie)
      decodedCookie <- sessionService.decodeCookie(validCookie.value)
    } yield decodedCookie
    println(userInfoOption)
    userInfoOption match {
      case Some(userInfo) => block(UserRequest(request, userInfo))
      case None => Future(Redirect("/auth"))
    }
  }

  private def validateCookie(cookie: Cookie): Option[Cookie] = {
    println(cookie.maxAge)
    val validMaxAge = cookie.maxAge match {
      case Some(maxAge) => maxAge > 0
      case None => false
    }
    val valid = validMaxAge // add other validations as needed
    if (valid) {
      println("Valid Cookie!")
      Some(cookie)
    } else {
      println("Invalid cookie")
      None
    }
  }

}
