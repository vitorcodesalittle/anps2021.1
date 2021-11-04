package model.transactions

case class Item(id: Option[Int], transactionId: Int, productId: Int, quantity: Int, price: Double)
