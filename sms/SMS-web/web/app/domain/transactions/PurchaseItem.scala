package domain.transactions

import domain.products.Product

case class ItemWithPurchasePrice(override val product: Product, override val quantity: Int, purchasePrice: Double) extends Item(product, quantity) {
  override def price: Double = purchasePrice * quantity
}
