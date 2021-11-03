package services.session

import play.api.mvc.{Request, WrappedRequest}


// The request of an authenticated user
case class UserRequest[A](request: Request[A], val userInfo: UserInfo) extends WrappedRequest[A](request)
