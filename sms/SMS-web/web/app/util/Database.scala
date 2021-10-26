package util

import domain.users.User
import play.api.db.Database

import java.sql.{ResultSet, ResultSetMetaData}
import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

class ScalaApplicationDatabase @Inject() (db: Database, databaseExecutionContext: ExecutionContext) {
  def updateSomething(): Future[List[User]] = {
    Future {
      db.withConnection { conn =>
        val result = conn.nativeSQL("SELECT * FROM users;")
        val dbResult = conn.createStatement().executeQuery(result)
        var users : List[User] = List()
        while (dbResult.next()) {
          val name: String = dbResult.getString("name")
          val email: String = dbResult.getString("email")
          val id: Int = dbResult.getInt("id")
          users = User(name, id, email, "") :: users
        }
        users
      }
    }(databaseExecutionContext)
  }
}