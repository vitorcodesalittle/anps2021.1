package model.store

import scala.concurrent.Future

trait StoreRepository {

  def create(store: Store): Future[Store]

  def getFromOwner(ownerId: Int): Future[Seq[Store]]

}
