package controllers

import model.Facade
import model.services.session.UserAction
import model.transactions.forms.SaleData
import play.api.libs.json.{JsError, JsSuccess, JsValue, Json}
import play.api.mvc.{Action, BaseController, ControllerComponents}

import javax.inject.Inject
import scala.concurrent.{Await, ExecutionContext, Future}

class TransactionController @Inject()(boundary: Facade, userAction: UserAction, val controllerComponents: ControllerComponents)(implicit ec: ExecutionContext)
  extends BaseController with play.api.i18n.I18nSupport {

  def doSale: Action[JsValue] = userAction(parse.json).async {
    implicit request ⇒ {
      println(request.body.toString)
      request.body.validate[SaleData] match {
        case JsError(errors) ⇒ Future {
          BadRequest(Json.obj("message" → JsError.toJson(errors)))
        }
        case JsSuccess(saleData, _) ⇒ {
          boundary.doSale(saleData, request.userInfo) map (sale => Ok(Json.toJson(sale))) fallbackTo(Future {
            InternalServerError(Json.obj("message" -> "Erro ao criar venda. tente mais tarde"))
          })
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
