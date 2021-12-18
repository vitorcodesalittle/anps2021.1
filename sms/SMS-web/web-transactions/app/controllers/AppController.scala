package controllers

import model.transactions.forms.{PurchaseData, SaleData}
import model.transactions.{Purchase, Sale, TransactionControl}
import play.api.libs.json.{JsError, JsSuccess, Json}

import javax.inject._
import play.api.mvc._

import java.time.Instant
import java.time.temporal.ChronoUnit
import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.duration.Duration

@Singleton
class AppController @Inject()(val controllerComponents: ControllerComponents, val transactionControl: TransactionControl)
                             (implicit val ec: ExecutionContext)
  extends BaseController {
  val maxWaitingDuration =  Duration.fromNanos(2e9)
  def getTransactions: Action[AnyContent] = Action.async { implicit request => {
    val from = getFromQueryOrDefaultTo("from", Instant.now().minus(7, ChronoUnit.DAYS))
    val to = getFromQueryOrDefaultTo("to", Instant.now())
    transactionControl.getTransactions(from, to) map { rows => Json.toJson(rows.map {
      case Purchase(transactionId, storeId, createdAt, description) => Json.toJson(Purchase(transactionId, storeId, createdAt, description) )
      case Sale(transactionId, storeId, createdAt, id, items, deliveryMethod, deliveryPrice, address) => Json.toJson(Sale(transactionId, storeId, createdAt, id, items, deliveryMethod, deliveryPrice, address))
    })} map (result => Ok(result)) recover ({
      case error => {
        println(error)
        InternalServerError("Internal Error")
      }
    })
  }}

  def createSale: Action[AnyContent] = Action.async { implicit request => {
    request.body.asJson match {
      case Some(data) => data.validate[SaleData] match {
        case JsSuccess(value, _) => transactionControl.createSale(value)
          .map(sale => Json.toJson(sale))
          .map(result => Ok(result))
          .recover(error => {
            println(error)
            InternalServerError("Internal Error")
          })
        case JsError(errors) => Future {
          println(errors) // TODO: proper validation
          Ok(Json.obj("message" -> "Bad sale data"))
        }
      }
      case None => Future {BadRequest(Json.obj("message" -> "Should have a json body"))}
    }
  }}

  def createPurchase: Action[AnyContent] = Action.async { implicit request => {
    println(request.body.asJson.toString)
    request.body.asJson match {
      case Some(data) => data.validate[PurchaseData] match {
        case JsSuccess(value, _) => transactionControl.createPurchase(value).map(purchase => Json.toJson(purchase)).map(result => Ok(result)).recover(error => {
          println(error)
          InternalServerError("Internal Error")
        })
        case JsError(errors) => Future {
          println(errors) // TODO: proper validation
          BadRequest(Json.obj("message" -> "Bad sale data"))
        }
      }
      case None => Future {BadRequest(Json.obj("message" -> "Should have a json body"))}
    }
  }}
  def getFromQueryOrDefaultTo(key: String, defaultTo: Instant)(implicit request: Request[AnyContent]): Instant = {
    request.queryString.get(key) match {
      case Some(p) => Instant.parse(p.mkString(""))
      case None => defaultTo
    }
  }
}
