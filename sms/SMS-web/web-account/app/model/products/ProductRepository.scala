package model.products

import model.transactions.forms.ItemData
import slick.dbio.DBIO

trait ProductRepository {
  def getAll(): DBIO[Seq[Product]]

  def getByStoreId(storeId: Int): DBIO[Seq[Product]]

  def getByName(name: String): DBIO[Product]

  def getById(id: Seq[Int]): DBIO[Seq[Product]]

  def create(product: Product): DBIO[Product]

  def update(productUpdate: Product): DBIO[Product]

  def remove(id: Int): DBIO[Int]

  def decrementStock(items: Seq[ItemData]): DBIO[Unit]
}
