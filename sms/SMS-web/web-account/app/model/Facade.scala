package model

import model.products.forms.ProductData
import model.products.{Product, ProductControl}
import model.services.session.UserInfo
import model.transactions._
import model.transactions.forms.{CacheFlowRequestData, SaleData}
import model.users.forms.{LoginData, SignUpData}
import model.users.{User, UserControl}
import play.api.mvc.Cookie
import javax.inject.{Inject, Singleton}
import scala.concurrent.Future

@Singleton
class Facade @Inject()(userControl: UserControl, productControl: ProductControl, transactionControl: TransactionControl) {
  // @deprecated
  def getAllProducts: Future[Seq[Product]] = productControl.getAllProducts()

  def getProductsFromUser(userInfo: UserInfo): Future[Seq[Product]] = productControl.getProductsFromUser(userInfo)

  def createProduct(userInfo: UserInfo, productData: ProductData): Future[products.Product] = productControl.createProduct(userInfo, productData)

  def signUp(signUpData: SignUpData): Future[User] = userControl.signUp(signUpData)

  def login(loginData: LoginData): Future[(User, Cookie)] = userControl.login(loginData)

  def getTransactions(userInfo: UserInfo): Future[Seq[Transaction]] = transactionControl.getTransactions(userInfo)

  def getSales(userInfo: UserInfo): Future[Seq[Sale]] = transactionControl.getSales(userInfo)

  def doSale(saleData: SaleData, userInfo: UserInfo): Future[Sale] = transactionControl.doSale(saleData, userInfo)

  def mountCashFlow(cacheFlowRequestData: CacheFlowRequestData, userInfo: UserInfo): Future[Seq[Option[Transaction]]] = transactionControl.mountCashFlow(cacheFlowRequestData, userInfo)
}
