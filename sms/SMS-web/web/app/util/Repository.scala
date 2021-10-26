package util

trait Repository[T] {
  def create(thing: T): Unit
  def exists(thing: T): Unit
  def update(thing: T): Unit
  def remove(thing: T): Unit
}
