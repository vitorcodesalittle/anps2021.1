package model.services.transporters

import model.transactions.Address
import play.api.libs.json.{JsError, JsSuccess}
import play.api.libs.ws.WSClient

import javax.inject.Singleton
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class TransporterAPI {
  def getDeliveryCost(address: Address, method: String)
                     (implicit ec: ExecutionContext, ws: WSClient): Future[TransporterResponse] = {
    val baseURL = "http://localhost:3000"
    ws.url(s"${baseURL}/transporters/${method}")
      .addQueryStringParameters("cep" -> address.zip)
      .execute()
      .map(request => {
        request.json.validate[TransporterResponse] match {
          case JsSuccess(value, _) => value
          case JsError(errors) => {
            println(errors)
            throw new Exception("Failed to parse transporter service response")
          }
        }
      })
  }
}
