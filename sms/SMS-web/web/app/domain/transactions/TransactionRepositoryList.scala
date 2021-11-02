package domain.transactions

import java.time.LocalDateTime
import scala.concurrent.{ExecutionContext, Future}

class TransactionRepositoryList()(implicit val executionContext: ExecutionContext) extends TransactionRepository {
  private var transactions: Seq[Transaction] = Seq()

  override def getSales(since: LocalDateTime, to: LocalDateTime): Future[Seq[Sale]] = Future {
    val sales: Seq[Sale] = transactions collect { case s: Sale => s }
    sales
  }

  override def getPurchases(since: LocalDateTime, to: LocalDateTime): Future[Seq[Purchase]] = Future {
    val purchases: Seq[Purchase] = transactions collect { case p: Purchase => p }
    purchases
  }

  override def getTransactions(since: LocalDateTime, to: LocalDateTime): Future[Seq[Transaction]] = Future {
    transactions
  }

  override def createSale(sale: Sale): Future[Sale] = Future {
    transactions = transactions :+ sale
    sale
  }

  override def createPurchase(purchase: Purchase): Future[Purchase] = Future {
    transactions = transactions :+ purchase
    purchase
  }

  override def deleteSale(saleId: Int): Future[Int] = Future {
    transactions = transactions filter {
      (tr: Transaction) =>
        tr match {
          case Sale(id, _, _, _, _) => id == saleId
          case _ => true
        }
    }
    1
  }
}
