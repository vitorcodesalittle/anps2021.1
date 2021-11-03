package services.session

import pdi.jwt.JwtSession
import play.api.Configuration
import play.api.libs.json.{Json, OWrites, Reads}
import play.api.mvc.Cookie
import util.ApplicationClock

import javax.inject.{Inject, Singleton}

@Singleton
class SessionService @Inject()(implicit val conf: Configuration) extends ApplicationClock {

  val AUTH_TOKEN: String = "authtoken"
  implicit val userInfoWrites: OWrites[UserInfo] = Json.writes[UserInfo]
  implicit val userInfoReads: Reads[UserInfo] = Json.reads[UserInfo]

  def encodeUserSession(user: UserInfo): Cookie = {
    val token = (JwtSession() + ("user", user)).serialize
    val maxAgeOption = conf.getOptional[Int]("play.http.session.maxAge")
    Cookie(name = AUTH_TOKEN, value = token, maxAge = maxAgeOption) // should later use userInfoWrites somehow
  }

  def decodeCookie(cookieValue: String): Option[UserInfo] = {
    val result = JwtSession.deserialize(cookieValue)
    result.getAs[UserInfo]("user")
  }
}
