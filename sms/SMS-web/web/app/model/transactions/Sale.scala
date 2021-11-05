package model.transactions

import model.global.Address

case class Sale(id: Option[Int], transactionId: Option[Int], deliveryMethod: String, deliveryPrice: Double, deliveryAddress: Address)

