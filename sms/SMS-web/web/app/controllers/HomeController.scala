package controllers

import javax.inject._
import play.api.mvc._
import scala.concurrent.{Await, ExecutionContext}

@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents)
                              (implicit  ec: ExecutionContext) extends BaseController {

  def index() = Action { implicit request: Request[AnyContent] => {
      Ok(views.html.index())
  }
   }

}
