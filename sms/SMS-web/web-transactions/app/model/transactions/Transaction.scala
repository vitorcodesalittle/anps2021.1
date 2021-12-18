package model.transactions

import play.api.libs.json.Json

import java.time.Instant

abstract class Transaction(transactionId: Int, storeId: Int, createdAt: Instant) {
}

