package model.transactions

import play.api.libs.json.{Json, OWrites, Reads}

case class Address(country: String, state: String, city: String, province: String, cep: String, extra: String, street: String)

object Address {
  implicit val addressReads: Reads[Address] = Json.reads[Address]
  implicit val addressWrites: OWrites[Address] = Json.writes[Address]
}