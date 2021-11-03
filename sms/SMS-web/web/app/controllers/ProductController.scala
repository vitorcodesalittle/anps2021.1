package controllers

import model.products.forms.ProductData
import model.products.{Product, ProductRepositoryList}
import play.api.data.Form
import play.api.data.Forms.{mapping, number, text}
import play.api.libs.json.{Json, OWrites}
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}

import javax.inject.Inject
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext}
import scala.util.{Failure, Success}

class ProductController @Inject()(repo: ProductRepositoryList, val controllerComponents: ControllerComponents)(implicit ec: ExecutionContext) extends BaseController with play.api.i18n.I18nSupport {

  implicit val productWrites: OWrites[Product] = Json.writes[Product]
  val productForm: Form[ProductData] = Form(
    mapping(
      "name" -> text(minLength = 1),
      "stock" -> number(min = 0),
      "suggestedPrice" -> number(min = 0),
      "barcode" -> text(minLength = 1),
    )(ProductData.apply)(ProductData.unapply)
  )

  def createProduct: Action[AnyContent] = Action {
    implicit request => {
      val productDataForm = productForm.bindFromRequest()
      val productData = productDataForm.get
      val product = Product(None, productData.name, productData.suggestedPrice, productData.stock, productData.barcode)
      val future = Await.ready(repo.create(product), Duration.Inf)
      val t = future.value.get
      t match {
        case Success(p) => Ok(Json.toJson(p))
        case Failure(e) => InternalServerError(e.toString) // don't do this
      }
    }
  }

  def getProducts: Action[AnyContent] = Action {
    implicit request => {
      Await.ready(repo.getAll(), Duration.Inf).value.get match {
        case Success(products) => Ok(views.html.products(products, productForm))
        case Failure(_) => InternalServerError("Failed to get products")
      }
    }
  }

}
