package util

import play.api.db.slick.DatabaseConfigProvider
import slick.dbio.{DBIOAction, Effect, NoStream}
import slick.jdbc.PostgresProfile

import javax.inject.Inject

abstract class DBRunner @Inject()(dbConfigProvider: DatabaseConfigProvider) {
  val dbConfig = dbConfigProvider.get[PostgresProfile]
  lazy val db = dbConfig.db

  def run[R, S <: NoStream, T <: Effect](action: DBIOAction[R, S, T]) = {
    db.run(action)
  }
}
