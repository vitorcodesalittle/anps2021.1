package model.services.encryption

import com.github.t3hnar.bcrypt._

import javax.inject.Singleton
import scala.util.Try

@Singleton
class EncryptionService {
  def verify(candidate: String, hash: String): Try[Boolean] = candidate.isBcryptedSafeBounded(hash)

  def hashPassword(password: String, salt: String): Try[String] = password.bcryptSafeBounded(salt)

  def hashPassword(password: String): Try[String] = password.bcryptSafeBounded(genSalt)

  def genSalt: String = generateSalt

}
