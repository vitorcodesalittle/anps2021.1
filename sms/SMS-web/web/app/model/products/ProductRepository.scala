package model.products

import slick.dbio.DBIO

trait ProductRepository {
  def getAll(): DBIO[Seq[Product]]

  def getByStoreId(storeId: Int): DBIO[Seq[Product]]

  def getByName(name: String): DBIO[Product]

  def getById(id: Int): DBIO[Product]

  def create(product: Product): DBIO[Product]

  def update(productUpdate: Product): DBIO[Product]

  def remove(id: Int): DBIO[Int]
}
