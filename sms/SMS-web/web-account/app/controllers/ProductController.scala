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
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

class ProductController @Inject()(boundary: Facade, userAction: UserAction, val controllerComponents: ControllerComponents)(implicit ec: ExecutionContext) extends BaseController with play.api.i18n.I18nSupport {

  def createProduct: Action[JsValue] = userAction(parse.json).async {
    implicit request => {
      request.body.validate[ProductData] match {
        case JsSuccess(productData, _) ⇒ {
          boundary.createProduct(request.userInfo, productData).map(p => Ok(Json.toJson(p)))
            .recoverWith((error) => {
              println(error)
              Future.successful(InternalServerError("Errorrrr"))
            })
        }
        case JsError(errors) ⇒ Future {
          println(errors)
          BadRequest
        }
      }

    }
  }

  def getProducts: Action[AnyContent] = userAction.async {
    implicit request => {
      boundary.getProductsFromUser(request.userInfo).map(products => {
        Ok(Json.toJson(products))
      }).recoverWith((e) => {
        println(e)
        Future.successful(InternalServerError("Faileddd"))
      })
    }
  }

}
