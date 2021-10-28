package domain.products

import scala.concurrent.Future

trait ProductRepository {
  def getAll(): Future[Seq[Product]]

  def getByName(name: String): Future[Product]

  def getById(id: Int): Future[Product]

  def create(product: Product): Future[Product]

  def update(productUpdate: Product): Future[Product]

  def remove(id: Int): Future[Int]
}
