package domain.products

import domain.products.exceptions.ProductExceptions.{ProductNameNotAllowedException, ProductNotFoundException}

import javax.inject.{Inject, Singleton}
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.util.{Failure, Success}

@Singleton
class ProductRepositoryList @Inject()(implicit val ec: ExecutionContext) extends ProductRepository {

  var products: List[Product] = List()

  override def getById(id: Int): Future[Product] = getOrThrow(product => product.id == id)

  def getOrThrow(fn: (Product => Boolean)): Future[Product] = Future {
    (products filter fn) match {
      case x :: xs => x
      case Nil => throw new ProductNotFoundException
    }
  }

  override def getAll(): Future[Seq[Product]] = Future {
    products
  }

  override def create(thing: Product): Future[Product] = Future {
    val productWithSameName = Await.ready(
      getByName(thing.name),
      Duration.Inf
    ).value.get
    productWithSameName match {
      case Success(p) => throw new ProductNameNotAllowedException
      case Failure(_) => {
        products = thing :: products
        thing
      }
    }
  }

  override def getByName(name: String): Future[Product] = getOrThrow(product => product.name == name)

  def exists(thing: Product): Future[Boolean] = Future {
    products.exists(product => product.name == thing.name)
  }

  override def update(thing: Product): Future[Product] = Future {
    products.find(product => product.id == thing) match {
      case Some(p) => p
      case None => throw new Exception("Product not found")
    }
  }

  override def remove(id: Int): Future[Int] = ???
}