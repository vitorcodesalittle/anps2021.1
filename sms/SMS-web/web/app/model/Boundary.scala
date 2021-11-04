package model

import model.products.{Product, ProductControl}
import model.products.forms.ProductData
import model.services.session.UserInfo
import model.users.forms.{LoginData, SignUpData}
import model.users.{User, UserControl}
import play.api.mvc.Cookie

import javax.inject.{Inject, Singleton}
import scala.util.Try

@Singleton
class Boundary @Inject()(userControl: UserControl, productControl: ProductControl) {
  def getAllProducts: Try[Seq[Product]] = productControl.getAllProducts()

  def getProductsFromUser(userInfo: UserInfo): Try[Seq[Product]] = productControl.getProductsFromUser(userInfo)

  def createProduct(userInfo: UserInfo, productData: ProductData): Try[products.Product] = productControl.createProduct(userInfo, productData)

  def signUp(signUpData: SignUpData): Try[User] = userControl.signUp(signUpData)

  def login(loginData: LoginData): Try[(User, Cookie)] = userControl.login(loginData)
}
