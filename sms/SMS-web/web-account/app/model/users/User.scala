package model.users

import play.api.libs.json.{Json, OWrites, Reads}

case class User(
                 id: Option[Int],
                 name: String,
                 email: String,
                 passwordHash: Option[String],
                 emailVerified: Option[Boolean] = Some(false)
               ) {
}

object User {
  implicit val reads: Reads[User] = Json.reads[User]
  implicit val writes: OWrites[User] = Json.writes[User]

}
