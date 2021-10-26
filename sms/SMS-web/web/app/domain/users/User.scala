package domain.users

case class User(id: Option[Int], name: String, email: String, passwordHash: Option[String], passwordSalt: Option[String], emailVerified: Option[Boolean] = Some(false)) {
}
