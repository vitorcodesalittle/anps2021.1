package util

import scala.concurrent.Future

trait Repository[T] {
  def create(thing: T): Future[T]

  def exists(thing: T): Future[Boolean]

  def update(thing: T): Future[T]

  def remove(thing: T): Future[Int]
}
