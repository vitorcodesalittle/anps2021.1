package model.transactions

import java.time.Instant

case class Transaction(id: Option[Int], storeId: Int, createdAt: Instant)
