package model.transactions.forms

import model.global.Address
import model.transactions.Item

case class TransactionData(items: Seq[Item])

case class SaleData(deliveryAddress: Address, transactionData: TransactionData)
