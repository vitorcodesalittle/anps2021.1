package model.store.forms

import play.api.data.FormError
import play.api.data.format.Formatter

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

  implicit val storeDataFormatter = new StoreDataFormatter
}
