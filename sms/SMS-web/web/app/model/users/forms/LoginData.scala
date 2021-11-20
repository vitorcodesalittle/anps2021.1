package model.users.forms

import play.api.libs.json.{Json, OWrites, Reads}

case class LoginData(email: String, password: String) {

}

object LoginData {
  implicit val reads: Reads[LoginData] = Json.reads[LoginData]
  implicit val writes: OWrites[LoginData] = Json.writes[LoginData]
}
