package model.products

import model.products.forms.ProductData
import model.services.session.UserInfo
import model.store.StoreRepositoryRDB

import javax.inject.Inject
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext}
import scala.util.Try

class ProductControl @Inject()(repo: ProductRepositoryRDB, storeRepo: StoreRepositoryRDB, implicit val ec: ExecutionContext) {
  def getAllProducts(): Try[Seq[Product]] = {
    Await.ready(repo.getAll(), Duration.Inf).value.get
  }

  def getProductsFromUser(user: UserInfo): Try[Seq[Product]] = {
    val productsFuture = for {
      store ← storeRepo.getFromOwner(user.id)
      products ← repo.getByStoreId(store.head.id.getOrElse(-1))
    } yield products
    Await.ready(productsFuture, Duration.Inf).value.get
  }

  def createProduct(userInfo: UserInfo, productData: ProductData): Try[Product] = {
    val productFuture = for {
      store ← storeRepo.getFromOwner(userInfo.id)
      product ← repo.create(Product(None, productData.name, productData.suggestedPrice, productData.stock, productData.barcode, storeId = store.head.id.getOrElse(-1)))
    } yield product
    Await.ready(productFuture, Duration.Inf).value.get
  }

}
