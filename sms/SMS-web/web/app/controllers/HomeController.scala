package controllers

import play.api.mvc._

import javax.inject._
import scala.concurrent.ExecutionContext

@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents)
                              (implicit ec: ExecutionContext) extends BaseController {

  def index() = Action {
    implicit request: Request[AnyContent] => {
      println("Hello!!!!")
      println("Where my views at")
      Ok(views.html.index())
    }
  }

}
