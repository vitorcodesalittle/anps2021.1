package controllers

import domain.products.{ProductRepositoryList, Product}
import domain.products.forms.ProductData
import play.api.data.Form
import play.api.data.Forms.{number, mapping, text}
import play.api.libs.json.{OWrites, Json}
import play.api.mvc.{Action, ControllerComponents, BaseController, AnyContent}

import javax.inject.Inject
import scala.concurrent.duration.Duration
import scala.concurrent.{ExecutionContext, Await}
import scala.util.{Success, Failure}

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
    val product: Product = Product(1, "asdasd", 123, 123)
    val future = Await.ready(repo.create(product), Duration.Inf)
    val t = future.value.get
    t match {
      case Success(p) => Ok(Json.toJson(p))
      case Failure(e) => InternalServerError(e.toString) // don't do this
    }
  }

}
