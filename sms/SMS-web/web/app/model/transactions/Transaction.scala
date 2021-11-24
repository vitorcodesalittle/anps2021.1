package model.transactions

import slick.lifted.MappedTo

import java.time.Instant

case class TransactionId(value: Int) extends MappedTo[Int]

abstract case class Transaction(transactionId: TransactionId, storeId: Int, createdAt: Instant, items: Option[Seq[Item]])
