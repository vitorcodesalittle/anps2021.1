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
    val token = (JwtSession() + ("id", user.id)).serialize
    println(token)
    val maxAge = conf.getOptional[Int]("play.http.session.maxAge")
    Cookie(name = AUTH_TOKEN, value = token, maxAge = maxAge) // should later use userInfoWrites somehow
  }

  def decodeCookie(cookieValue: String): Option[UserInfo] = {
    println("Receive cookieValue = " + cookieValue)
    val result = JwtSession.deserialize(cookieValue).getAs[UserInfo]("")
    println(result)
    result
  }
}
