package model.services.transporters

import play.api.libs.json.Json

import java.time.Instant

case class TransporterResponse(price: Double, deadline: Instant)
object TransporterResponse {
  implicit val reads = Json.reads[TransporterResponse]
  implicit val writes = Json.writes[TransporterResponse]
}
