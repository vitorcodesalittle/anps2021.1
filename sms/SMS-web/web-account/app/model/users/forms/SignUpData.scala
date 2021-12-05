package model.users.forms

import model.store.forms.StoreData
import play.api.libs.functional.syntax.toFunctionalBuilderOps
import play.api.libs.json.{JsPath, Json, OWrites, Reads}

case class SignUpData(name: String, email: String, password: String, storeData: StoreData)

object SignUpData {
  implicit val reads: Reads[SignUpData] = (
    (JsPath \ "name").read[String] and
      (JsPath \ "email").read[String] and
      (JsPath \ "password").read[String] and
      (JsPath \ "storeName").read[StoreData]
  )(SignUpData.apply _)
  implicit val writes: OWrites[SignUpData] = Json.writes[SignUpData]
}

