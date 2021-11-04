package model

import model.products.ProductControl
import model.products.forms.ProductData
import model.users.forms.{LoginData, SignUpData}
import model.users.{User, UserControl}
import play.api.mvc.Cookie

import javax.inject.{Inject, Singleton}
import scala.util.Try

@Singleton
class Boundary @Inject()(userControl: UserControl, productControl: ProductControl) {
  def getAllProducts: Try[Seq[products.Product]] = productControl.getAllProducts()

  def createProduct(productData: ProductData): Try[products.Product] = productControl.createProduct(productData)

  def signUp(signUpData: SignUpData): Try[User] = userControl.signUp(signUpData)

  def login(loginData: LoginData): Try[(User, Cookie)] = userControl.login(loginData)
}
