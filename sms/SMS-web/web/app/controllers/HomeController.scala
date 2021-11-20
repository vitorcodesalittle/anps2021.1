package controllers

import play.api.Configuration
import play.api.mvc._

import javax.inject._
import scala.concurrent.ExecutionContext

@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents, val assets: Assets, val config: Configuration)
                              (implicit ec: ExecutionContext) extends BaseController {

  def index: Action[AnyContent] = assets.at("index.html")

  def assetOrDefault(resource: String): Action[AnyContent] = if (resource.startsWith(config.get[String]("apiPrefix"))) {
    Action {
      NotFound("This page is not found")
    }
  } else {
    if (resource.contains(".")) assets.at(resource) else index
  }
}
