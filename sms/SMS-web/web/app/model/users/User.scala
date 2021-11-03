package model.users

case class User(
                 id: Option[Int],
                 name: String,
                 email: String,
                 passwordHash: Option[String],
                 emailVerified: Option[Boolean] = Some(false)
               ) {
}


