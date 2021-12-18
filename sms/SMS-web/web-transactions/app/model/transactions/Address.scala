package model.transactions

import play.api.libs.json.Json

case class Address(country: Option[String],
                   state: Option[String],
                   city: Option[String],
                   street: Option[String],
                   extra: Option[String],
                   zip: String)

object Address {
  implicit val reads = Json.reads[Address]
  implicit val writes = Json.writes[Address]
}
