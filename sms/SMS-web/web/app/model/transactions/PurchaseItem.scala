package model.transactions

import model.products.Product

case class PurchaseItem(product: Product, quantity: Int, purchasePrice: Double) extends Item {
  override def price: Double = purchasePrice * quantity
}
