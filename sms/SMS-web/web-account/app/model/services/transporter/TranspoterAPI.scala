package model.services.transporter

import model.transactions.Address
import play.api.libs.json.{JsError, JsSuccess, Json}
import play.api.libs.ws
import play.api.libs.ws.WSClient

import java.time.Instant
import scala.concurrent.{ExecutionContext, Future}

sealed case class Transporter(name: String)

object Transporter {
  def correios = Transporter("CORREIOS")
  def sedex = Transporter("SEDEX")
}

case class TransporterResponse(price: Double, deadline: Instant)

object TransporterResponse {
  implicit val jsonReads = Json.reads[TransporterResponse]
}

trait TransporterApi {
  def getTransporters: List[Transporter] = List(
    Transporter.correios,
    Transporter.sedex
  )

  def getDeliveryCost(address: Address, method: Transporter)
                     (implicit ec: ExecutionContext, ws: WSClient): Future[TransporterResponse] = {
    val baseURL = "http://transporterservice:3000"
    ws.url(s"${baseURL}/transporters/${method.name}")
      .addQueryStringParameters("cep" → address.cep)
      .execute()
      .map(request ⇒ {
        request.json.validate[TransporterResponse] match {
          case JsSuccess(value, _) ⇒ value
          case JsError(errors) ⇒ {
            println(errors)
            throw new Exception("Failed to parse transporter service response")
          }
        }
      })
  }
}
