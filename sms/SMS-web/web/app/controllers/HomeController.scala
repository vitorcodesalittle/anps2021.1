package controllers

import javax.inject._
import play.api.mvc._
import domain.users.User
import util.ScalaApplicationDatabase

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext}
/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(val db: ScalaApplicationDatabase, val cc: ControllerComponents)
                              (implicit  ec: ExecutionContext) extends BaseController {

  def index() = Action { implicit request: Request[AnyContent] => {
      val user = User("asdijasd", 1, "asd@asdas.com", "12345")
      val returned = db.updateSomething()
      val returned2 = Await.ready(returned map println, Duration.Inf)
      println(returned2)
      Ok(views.html.index())
  }
   }

  override protected def controllerComponents: ControllerComponents = cc
}
