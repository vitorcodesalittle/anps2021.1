package controllers

import domain.users.forms.UserSignUp

import javax.inject.{Inject, Singleton}
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration.Duration
import play.api.libs.json.Json
import play.api.mvc._
import domain.users.{User, UserRepositoryBDR}
import play.api.data.Form
import play.api.data.Forms.{mapping, text}
import play.api.http.Writeable.wByteArray
import play.api.i18n.Messages.implicitMessagesProviderToMessages
import play.api.i18n.{Lang, Langs, Messages, MessagesApi, MessagesImpl}
import play.filters.csrf.CSRF

trait PostRequestHeader extends MessagesRequestHeader with PreferredMessagesProvider
class PostRequest[A](request: Request[A], val messagesApi: MessagesApi) extends WrappedRequest(request) with PostRequestHeader
@Singleton
class UserController @Inject() (val repo: UserRepositoryBDR, val controllerComponents: ControllerComponents, implicit val langs: Langs, messagesApi: MessagesApi)
  (implicit ec: ExecutionContext) extends BaseController {
  implicit val lang: Lang = langs.availables.head

  implicit val userWrites = Json.writes[User]
  val userForm = Form(
    mapping(
      "name" -> text,
      "email" -> text,
      "password" -> text,
    )(UserSignUp.apply)(UserSignUp.unapply)
  )
  val messages: Messages = MessagesImpl(lang, messagesApi)
  def index = Action { implicit request =>
    Ok(views.html.user(userForm)(new PostRequest(request, messagesApi)))
  }

  def login(): Action[AnyContent] = Action {
    NotImplemented("Sorry")
  }

  def signUp() = Action { implicit  request => {
      val userData = userForm.bindFromRequest.get
      // @TODO: validation
      val user = User(id = None, email = userData.email, name = userData.name, passwordHash = Some(hashPassword(userData.password)), passwordSalt = Some(genSalt()))
      println(user)
      val savedUser = Await.result(repo.create(user), Duration.Inf) // @TODO: Duration.Inf ???
      Ok(Json.toJson(savedUser))
    }
  }
  def hashPassword(str: String) = str // @TODO
  def genSalt(): String = "asdashd" // @TODO
  def verifyPassword(user: User, password: String): Future[Boolean] = ???
  def verifyPasswordStrength(password: String): Future[Int] = ???

}

