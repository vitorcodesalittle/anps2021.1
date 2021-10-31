package domain.transactions

import domain.products.Product

case class PurchaseItem(override val product: Product, override val quantity: Int, purchasePrice: Double) extends SaleItem(product, quantity) {
  override def price: Double = purchasePrice * quantity
}
