package model.users.forms

import model.store.forms.StoreData
import play.api.libs.json.{Json, OWrites, Reads}

case class SignUpData(name: String, email: String, password: String, storeData: StoreData)

object SignUpData {
  implicit val reads: Reads[SignUpData] = Json.reads[SignUpData]
  implicit val writes: OWrites[SignUpData] = Json.writes[SignUpData]
}

