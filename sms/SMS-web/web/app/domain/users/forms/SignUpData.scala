package domain.users.forms

case class SignUpData(name: String, email: String, password: String)

case class LoginData(email: String, password: String)
