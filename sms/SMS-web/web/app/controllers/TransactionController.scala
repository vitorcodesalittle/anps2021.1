package controllers

import model.Facade
import model.services.session.UserAction
import model.transactions.forms.SaleData
import play.api.libs.json.{JsError, JsSuccess, JsValue, Json}
import play.api.mvc.{Action, BaseController, ControllerComponents}

import javax.inject.Inject
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext}
import scala.util.{Failure, Success}

class TransactionController @Inject()(boundary: Facade, userAction: UserAction, val controllerComponents: ControllerComponents)(implicit ec: ExecutionContext)
  extends BaseController{

  def doSale: Action[JsValue] = userAction(parse.json) {
    implicit request ⇒ {
      request.body.validate[SaleData] match {
        case JsError(errors) ⇒ {
          BadRequest(Json.obj("message" → JsError.toJson(errors)))
        }
        case JsSuccess(saleData, _) ⇒ {
          val saleFuture = Await.ready(boundary.doSale(saleData, request.userInfo), Duration.Inf).value.get
          saleFuture match {
            case Failure(exception) => {
              println(exception)
              InternalServerError(Json.obj("message" → "My bad bro"))
            }
            case Success(sale) => {
              Ok(Json.toJson(sale))
            }
          }
        }
      }
    }
  }
  //
  //  def mountCashFlow: Action[AnyContent] = userAction {
  //    implicit request ⇒ {
  //      val cashFlowData = cashFlowForm.bindFromRequest().get
  //      boundary.mountCashFlow(cashFlowData, request.userInfo) match {
  //        case Success(cashFlow) ⇒ Ok(s"cash flow raw data: $cashFlow")
  //        case Failure(e) ⇒ {
  //          println(e)
  //          InternalServerError("Não foi possível recuperar o fluxo de caixa. Tente mais tarde")
  //        }
  //      }
  //    }
  //  }
}
