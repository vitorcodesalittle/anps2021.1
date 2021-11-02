package domain.transactions

import domain.products.Product

case class SaleItem(product: Product, quantity: Int) extends Item {
  override def price: Double = product.salePrice * quantity
}
