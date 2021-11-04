package model.products

case class Product(id: Option[Int], name: String, suggestedPrice: Double, stock: Int, barcode: String, storeId: Int)
