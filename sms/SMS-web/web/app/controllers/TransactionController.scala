package controllers

import model.Boundary
import model.services.session.UserAction
import model.transactions.forms.SaleData
import play.api.libs.json.{JsError, JsSuccess, JsValue, Json}
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}

import javax.inject.Inject
import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

class TransactionController @Inject()(boundary: Boundary, userAction: UserAction, val controllerComponents: ControllerComponents)(implicit ec: ExecutionContext)
  extends BaseController with play.api.i18n.I18nSupport {

  //  val cashFlowForm: Form[CacheFlowRequestData] = ???

  def doSale: Action[JsValue] = userAction(parse.json) {
    implicit request ⇒ {
      request.body.validate[SaleData] match {
        case JsError(errors) ⇒ {
          BadRequest(Json.obj("message" → JsError.toJson(errors)))
        }
        case JsSuccess(saleData, _) ⇒ {
          boundary.doSale(saleData, request.userInfo) match {
            case Success((transaction, sale, items)) ⇒ Ok(s"Venda registrada $transaction $sale $items")
            case Failure(e) ⇒ {
              println(e)
              InternalServerError("Não foi possível registrar venda. Tente mais tarde")
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

  def index: Action[AnyContent] = userAction {
    implicit request ⇒ {
      boundary.getTransactions(request.userInfo) match {
        case Success(transactions) ⇒ Ok(views.html.transactions(transactions))
        case Failure(e) ⇒ {
          println(e)
          InternalServerError("Não foi possível recuperar as transações")
        }
      }
    }
  }
}
