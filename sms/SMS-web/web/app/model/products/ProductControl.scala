package model.products

import model.products.forms.ProductData

import javax.inject.Inject
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.util.Try

class ProductControl @Inject()(repo: ProductRepositoryList) {
  def getAllProducts(): Try[Seq[Product]] = {
    Await.ready(repo.getAll(), Duration.Inf).value.get
  }


  def createProduct(productData: ProductData): Try[Product] = {
    val product = Product(None, productData.name, productData.suggestedPrice, productData.stock, productData.barcode)
    Await.ready(repo.create(product), Duration.Inf).value.get
  }

}
