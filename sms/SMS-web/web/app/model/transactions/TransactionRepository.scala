package model.transactions

import slick.dbio.DBIO

import java.time.Instant

trait TransactionRepository {

  def getAllTransactions(storeId: Int): DBIO[Seq[(Transaction, Option[Sale], Option[Purchase], Seq[(Item, Product)])]]

  def getTransactions(storeId: Int, since: Instant, to: Instant): DBIO[Seq[(Transaction, Option[Sale], Option[Purchase], Seq[Item])]]

  def createSaleWithTransaction(transaction: Transaction, sale: Sale, items: Seq[Item]): DBIO[(Transaction, Sale, Seq[Item])]

  def createSale(sale: Sale): DBIO[Sale]

  def getSales(since: Instant, to: Instant): DBIO[Seq[Sale]]

  def getPurchases(since: Instant, to: Instant): DBIO[Seq[Purchase]]

  def createPurchase(purchase: Purchase): DBIO[Purchase]

  def deleteSale(saleId: Int): DBIO[Int]
}
