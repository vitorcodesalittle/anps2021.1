package model.store.forms

import play.api.data.FormError
import play.api.data.format.Formatter
import play.api.libs.json.{JsArray, JsBoolean, JsError, JsNull, JsNumber, JsObject, JsPath, JsString, JsSuccess, Json, OWrites, Reads}

case class StoreData(name: String)

object StoreData {
  class StoreDataFormatter extends Formatter[StoreData] {
    override def bind(key: String, data: Map[String, String]): Either[Seq[FormError], StoreData] = {
      data.get("storeName") match {
        case Some(name) ⇒ Right(StoreData(name = name))
        case None ⇒ {
          val formErrors = Seq(new FormError(key, Seq("empty store name")))
          Left(formErrors)
        }
      }
    }

    override def unbind(key: String, value: StoreData): Map[String, String] = {
      Map(("storeName", value.name))
    }
  }

  implicit val storeDataFormatter: StoreDataFormatter = new StoreDataFormatter

  implicit val reads: Reads[StoreData] = Reads[StoreData] {
    case JsString(value) => JsSuccess(StoreData(value))
    case _ => JsError()
  }
  implicit val writes: OWrites[StoreData] = Json.writes[StoreData]
}
