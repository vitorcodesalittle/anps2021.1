package model.users

import model.services.encryption.EncryptionService
import model.services.session.{SessionService, UserInfo}
import model.store.{Store, StoreRepositoryRDB}
import model.users.exceptions.UserExceptions.{PasswordInvalidException, PasswordTooWeakException}
import model.users.forms.{LoginData, SignUpData}
import play.api.mvc.Cookie

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

@Singleton
class UserControl @Inject()(repo: UserRepositoryBDR, storeRepo: StoreRepositoryRDB, sessionService: SessionService, encryptionService: EncryptionService)(implicit val ec: ExecutionContext) {

  def login(loginData: LoginData): Future[(User, Cookie)] = {
      repo.run(for {
        user <- repo getByEmail loginData.email
        store <- storeRepo getFromOwner user.id.get
      } yield (user, store)) map (result => {
        val (user, store) = result
        verifyPassword(user, loginData.password) match {
          case Failure(exception) => throw exception
          case Success(valid) => if (valid) {
            (user, sessionService.encodeUserSession(UserInfo(user.id.get, store.head.id.get)))
          } else {
            throw new PasswordInvalidException
          }
        }
      })
  }

  def signUp(signUpData: SignUpData): Future[User] = {
    val passwordStrength = verifyPasswordStrength(signUpData.password)
    if (passwordStrength < 6) {
      Future.failed(new PasswordTooWeakException)
    } else {
      Future.fromTry(encryptionService.hashPassword(signUpData.password))
        .flatMap(passwordHash => {
          repo.run((
            for {
              savedUser <- repo create User(id = None, email = signUpData.email, name = signUpData.name, passwordHash = Some(passwordHash))
              _ <- storeRepo create Store(id = None, name = signUpData.storeData.name, ownerId = savedUser.id.get)
            } yield savedUser)
          )
        })
    }
  }

  def verifyPassword(user: User, password: String): Try[Boolean] = user.passwordHash match {
    case Some(passwordHash) => encryptionService.verify(password, passwordHash)
    case None => Failure(new Exception("User não tem hash de senha registrada"))
  }

  def verifyPasswordStrength(password: String): Int = password.length // @TODO

}
