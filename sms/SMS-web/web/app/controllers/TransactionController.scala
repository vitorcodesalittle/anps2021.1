package controllers

import akka.http.scaladsl.model.DateTime
import domain.transactions.{Sale, TransactionRepositoryList}
import play.api.mvc.{Action, ControllerComponents, BaseController, AnyContent}

import javax.inject.Inject
import javax.transaction.Transaction
import scala.concurrent.{ExecutionContext, Future}

class TransactionController @Inject()(repo: TransactionRepositoryList, val controllerComponents: ControllerComponents)(implicit ec: ExecutionContext) extends BaseController with play.api.i18n.I18nSupport {
  def doSale: Action[AnyContent] = ???

  def doPurchase: Action[AnyContent] = ???

  def mountCashFlow: Action[AnyContent] = ???

  def aggregateTransactions(start: DateTime, end: DateTime): Future[Map[DateTime, List[Transaction]]] = ???

  def hasSufficientStock(sale: Sale): Future[Boolean] = ???
}
