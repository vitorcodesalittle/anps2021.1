package model.transactions.errors

class InsufficientStock(message: String, missingMap: Map[Int, Int]) extends Exception(message) {
}
