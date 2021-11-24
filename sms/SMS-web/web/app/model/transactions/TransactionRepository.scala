package model.transactions

import slick.dbio.DBIO

import java.time.Instant

trait TransactionRepository {

  def getAllTransactions(storeId: Int): DBIO[Seq[Transaction]]

  def getTransactions(storeId: Int, since: Instant, to: Instant): DBIO[Seq[Transaction]]

  def createSale(sale: Sale): DBIO[Sale]

  def createPurchase(purchase: Purchase): DBIO[Purchase]

  def deleteTransaction(transactionId: Int): DBIO[Int]
}
