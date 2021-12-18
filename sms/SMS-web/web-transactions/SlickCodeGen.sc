import slick.codegen.SourceCodeGenerator
import slick.jdbc.PostgresProfile

import scala.concurrent.{Await, ExecutionContext}
import scala.concurrent.duration.DurationInt

val dir = "/home/vilma/github.com/vlma/anps2021.1/sms/SMS-web/web-transactions/app/model/"
val outputDir = dir
val username = "user"
val password = "senha"
val url = "jdbc:postgresql://localhost:5432/sms"
val jdbcDriver = "org.postgresql.Driver"
val profile = "slick.jdbc.PostgresProfile"
val pkg = "model.dao"

/**
 *  This customizes the Slick code generator. We only do simple name mappings.
 *  For a more advanced example see https://github.com/cvogt/slick-presentation/tree/scala-exchange-2013
 */
object CustomizedCodeGenerator{
  implicit val ec = ExecutionContext.global
  def main(): Unit = {
    Await.ready(
      codegen.map(_.writeToFile(
        profile,
        outputDir,
        pkg
      )),
      20.seconds
    )
  }

  val db = PostgresProfile.api.Database.forURL(url,driver=jdbcDriver,user=username, password=password)
  val codegen = db.run{
    PostgresProfile.defaultTables.flatMap( PostgresProfile.createModelBuilder(_,ignoreInvalidDefaults = false).buildModel )
  }.map{ model =>
    new slick.codegen.SourceCodeGenerator(model){
      // override generator responsible for tables
      override def Table = new Table(_){
        table =>
        override def autoIncLastAsOption: Boolean = true
      }
    }
  }
}

CustomizedCodeGenerator.main()