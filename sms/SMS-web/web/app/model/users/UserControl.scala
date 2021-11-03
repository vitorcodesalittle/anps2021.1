package model.users

import model.services.encryption.EncryptionService
import model.services.session.{SessionService, UserInfo}
import model.users.exceptions.UserExceptions.PasswordTooWeakException
import model.users.forms.{LoginData, SignUpData}
import play.api.mvc.Cookie

import javax.inject.{Inject, Singleton}
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success, Try}

@Singleton
class UserControl @Inject()(repo: UserRepositoryBDR, sessionService: SessionService, encryptionService: EncryptionService) {

  def login(loginData: LoginData): Try[(User, Cookie)] = {
    val userFuture = repo getByEmail loginData.email
    for {
      user <- Await.ready(userFuture, Duration.Inf).value.get
      validUser <- verifyPassword(user, loginData.password)
      if validUser
      authCookie <- user.id match {
        case Some(id) => Success(sessionService.encodeUserSession(UserInfo(id)))
        case None => Failure(new RuntimeException())
      }
    } yield (user, authCookie)
  }

  def signUp(signUpData: SignUpData): Try[User] = {
    val passwordStrength = verifyPasswordStrength(signUpData.password)
    if (passwordStrength < 6) {
      Failure(new PasswordTooWeakException)
    } else {
      val user = User(id = None, email = signUpData.email, name = signUpData.name, passwordHash = None)
      Await.ready(repo.create(user, signUpData.password), Duration.Inf).value.get // @TODO: Duration.Inf ???
    }
  }

  def verifyPassword(user: User, password: String): Try[Boolean] = user.passwordHash match {
    case Some(passwordHash) => encryptionService.verify(password, passwordHash)
    case None => Failure(new Exception("User não tem hash de senha registrada"))
  }

  def verifyPasswordStrength(password: String): Int = password.length // @TODO

}
