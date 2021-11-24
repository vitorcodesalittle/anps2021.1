package model.products

import model.products.forms.ProductData
import model.services.session.UserInfo
import model.store.StoreRepositoryRDB

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class ProductControl @Inject()(repo: ProductRepositoryRDB, storeRepo: StoreRepositoryRDB, implicit val ec: ExecutionContext) {
  def getAllProducts(): Future[Seq[Product]] = {
    repo.run(repo.getAll)
  }

  def getProductsFromUser(user: UserInfo): Future[Seq[Product]] = {
    repo.run(for {
      store ← storeRepo.getFromOwner(user.id)
      products ← repo.getByStoreId(store.head.id.getOrElse(-1))
    } yield products)
  }

  def createProduct(userInfo: UserInfo, productData: ProductData): Future[Product] = {
    repo.run(for {
      store ← storeRepo.getFromOwner(userInfo.id)
      product ← repo.create(Product(None, productData.name, productData.suggestedPrice, productData.stock, productData.barcode, storeId = store.head.id.getOrElse(-1)))
    } yield product)
  }

}
