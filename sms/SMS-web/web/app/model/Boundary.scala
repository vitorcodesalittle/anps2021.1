package model

import model.products.forms.ProductData
import model.products.{Product, ProductControl}
import model.services.session.UserInfo
import model.transactions.forms.{CacheFlowRequestData, SaleData}
import model.transactions.{Item, Sale, Transaction, TransactionControl}
import model.users.forms.{LoginData, SignUpData}
import model.users.{User, UserControl}
import play.api.mvc.Cookie

import javax.inject.{Inject, Singleton}
import scala.util.Try

@Singleton
class Boundary @Inject()(userControl: UserControl, productControl: ProductControl, transactionControl: TransactionControl) {
  // @deprecated
  def getAllProducts: Try[Seq[Product]] = productControl.getAllProducts()

  def getProductsFromUser(userInfo: UserInfo): Try[Seq[Product]] = productControl.getProductsFromUser(userInfo)

  def createProduct(userInfo: UserInfo, productData: ProductData): Try[products.Product] = productControl.createProduct(userInfo, productData)

  def signUp(signUpData: SignUpData): Try[User] = userControl.signUp(signUpData)

  def login(loginData: LoginData): Try[(User, Cookie)] = userControl.login(loginData)

  def getTransactions(userInfo: UserInfo): Try[Seq[(Transaction, Option[Sale], Option[Transaction], Seq[Item])]] = transactionControl.getTransactions(userInfo)

  def doSale(saleData: SaleData, userInfo: UserInfo): Try[(Transaction, Sale, Seq[Item])] = transactionControl.doSale(saleData, userInfo)

  def mountCashFlow(cacheFlowRequestData: CacheFlowRequestData, userInfo: UserInfo): Try[Seq[Option[(Transaction, Seq[Item])]]] = transactionControl.mountCashFlow(cacheFlowRequestData, userInfo)
}
