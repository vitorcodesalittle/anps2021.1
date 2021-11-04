package controllers

import model.Boundary
import model.services.session.UserAction
import model.transactions.forms.{CacheFlowRequestData, SaleData}
import play.api.data.Form
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}

import javax.inject.Inject
import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

class TransactionController @Inject()(boundary: Boundary, userAction: UserAction, val controllerComponents: ControllerComponents)(implicit ec: ExecutionContext)
  extends BaseController with play.api.i18n.I18nSupport {

  val saleForm: Form[SaleData] = ???
  val cashFlowForm: Form[CacheFlowRequestData] = ???

  def doSale: Action[AnyContent] = userAction {
    implicit request ⇒ {
      val saleData = saleForm.bindFromRequest().get
      boundary.doSale(saleData, request.userInfo) match {
        case Success((transaction, sale, items)) ⇒ Ok(s"Venda registrada $transaction $sale $items")
        case Failure(e) ⇒ {
          println(e)
          InternalServerError("Não foi possível registrar venda. Tente mais tarde")
        }
      }

    }
  }

  def mountCashFlow: Action[AnyContent] = userAction {
    implicit request ⇒ {
      val cashFlowData = cashFlowForm.bindFromRequest().get
      boundary.mountCashFlow(cashFlowData, request.userInfo) match {
        case Success(cashFlow) ⇒ Ok(s"cash flow raw data: $cashFlow")
        case Failure(e) ⇒ {
          println(e)
          InternalServerError("Não foi possível recuperar o fluxo de caixa. Tente mais tarde")
        }
      }
    }
  }

  def getTransactions: Action[AnyContent] = userAction {
    implicit request ⇒ {
      boundary.getTransactions(request.userInfo) match {
        case Success(transactions) ⇒ Ok(s"$transactions")
        case Failure(e) ⇒ {
          println(e)
          InternalServerError("Não foi possível recuperar as transações")
        }
      }
    }
  }
}
