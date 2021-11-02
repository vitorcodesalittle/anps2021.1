package domain.transactions

import domain.products.Product

case class PurchaseItem(product: Product, quantity: Int, purchasePrice: Double) extends Item {
  override def price: Double = purchasePrice * quantity
}
