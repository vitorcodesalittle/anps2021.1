package model.transactions

import model.products.Product

case class SaleItem(product: Product, quantity: Int) extends Item {
  override def price: Double = product.suggestedPrice * quantity
}
