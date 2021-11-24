package controllers

import model.Facade
import model.products.Product
import model.products.forms.ProductData
import model.services.session.UserAction
import play.api.data.Form
import play.api.data.Forms.{mapping, number, text}
import play.api.libs.json._
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}

import javax.inject.Inject
import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success, Try}

class ProductController @Inject()(boundary: Facade, userAction: UserAction, val controllerComponents: ControllerComponents)(implicit ec: ExecutionContext) extends BaseController with play.api.i18n.I18nSupport {

  implicit val productWrites: OWrites[Product] = Json.writes[Product]
  val productForm: Form[ProductData] = Form(
    mapping(
      "name" -> text(minLength = 1),
      "stock" -> number(min = 0),
      "suggestedPrice" -> number(min = 0),
      "barcode" -> text(minLength = 1),
    )(ProductData.apply)(ProductData.unapply)
  )

  def createProduct: Action[JsValue] = userAction(parse.json) {
    implicit request => {
      request.body.validate[ProductData] match {
        case JsSuccess(productData, _) ⇒ {
          val productTry: Try[Product] = boundary.createProduct(request.userInfo, productData)
          productTry match {
            case Success(p) => Ok(Json.toJson(p))
            case Failure(e) => InternalServerError(e.toString) // don't do this
          }
        }
        case JsError(errors) ⇒ {
          println(errors)
          BadRequest
        }
      }

    }
  }

  def getProducts: Action[AnyContent] = userAction {
    implicit request => {
      val productsTry: Try[Seq[Product]] = boundary.getProductsFromUser(request.userInfo)
      productsTry match {
        case Success(products) => Ok(Json.toJson(products))
        case Failure(_) => InternalServerError("Failed to get products")
      }
    }
  }

}
