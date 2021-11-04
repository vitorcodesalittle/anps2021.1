package model.products

import model.store.Stores
import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag

class Products(tag: Tag) extends Table[Product](tag, "PRODUCTS") {
  def id: Rep[Int] = column[Int]("ID", O.PrimaryKey, O.AutoInc)

  def name: Rep[String] = column[String]("NAME", O.Length(256))

  def barcode: Rep[String] = column[String]("BARCODE", O.Length(256))

  def suggestedPrice: Rep[Double] = column[Double]("SUGGESTED_PRICE")

  def storeId: Rep[Int] = column[Int]("STORE_ID")

  def stock: Rep[Int] = column[Int]("STOCK")

  def store = foreignKey("STORE", storeId, stores)(_.id)

  override def * = (id.?, name, suggestedPrice, stock, barcode, storeId) <> (Product.tupled, Product.unapply)

  lazy val stores = TableQuery[Stores]
}
