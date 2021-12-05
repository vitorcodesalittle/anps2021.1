package model.users.exceptions

object UserExceptions {
  class UserNotFoundException extends Exception

  class PasswordTooWeakException extends Exception

  class PasswordInvalidException extends Exception
}
