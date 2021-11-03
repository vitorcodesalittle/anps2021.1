package domain.users

import play.api.libs.json.{Json, OWrites}

trait UserJson {

  implicit val userWrites: OWrites[User] = Json.writes[User]
}
