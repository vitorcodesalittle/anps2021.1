package model.transactions

import play.api.libs.json.{Json, Reads}

case class Address(country: String, state: String, city: String, province: String, cep: String)

object Address {
  implicit val addressReads: Reads[Address] = Json.reads[Address]
}