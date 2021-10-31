package domain.transactions

import domain.products.Product

case class SaleItem(product: Product, quantity: Int) {
  def price: Double = product.salePrice * quantity
}
