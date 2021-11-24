package model.store

import slick.dbio.DBIO

trait StoreRepository {

  def create(store: Store): DBIO[Store]

  def getFromOwner(ownerId: Int): DBIO[Seq[Store]]

}
