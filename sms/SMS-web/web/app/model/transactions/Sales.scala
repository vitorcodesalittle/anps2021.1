package model.transactions

import model.global.Address
import slick.jdbc.PostgresProfile.api._

class Sales(tag: Tag) extends Table[Sale](tag, "SALES") {
  def id: Rep[Int] = column[Int]("ID", O.PrimaryKey, O.AutoInc)

  def transactionId: Rep[Int] = column[Int]("TRANSACTION_ID")

  def transaction = foreignKey("TRANSACTION", transactionId, transactions)(_.id)

  def deliveryPrice: Rep[Double] = column[Double]("DELIVERY_PRICE")

  def deliveryMethod: Rep[String] = column[String]("DELIVERY_METHOD")

  def addressCEP: Rep[String] = column[String]("ADDRESS_CEP")

  def addressStreet: Rep[String] = column[String]("ADDRESS_STREET")

  def addressState: Rep[String] = column[String]("ADDRESS_STATE")

  def addressCountry: Rep[String] = column[String]("ADDRESS_COUNTRY")

  def addressExtra: Rep[String] = column[String]("ADDRESS_EXTRA")

  def * = (id.?, transactionId.?, deliveryMethod, deliveryPrice, (addressCEP, addressStreet, addressState, addressCountry, addressExtra)).shaped <> ( {
    case (id, transactionId, deliveryMethod, deliveryPrice, address) ⇒ Sale(id, transactionId, deliveryMethod, deliveryPrice, (Address.apply _).tupled.apply(address))
  }, { s: Sale ⇒ {
    def f1(a: Address) = {
      Address.unapply(a).get
    }

    Some((s.id, s.transactionId, s.deliveryMethod, s.deliveryPrice, f1(s.deliveryAddress)))
  }
  })

  lazy val transactions = TableQuery[Transactions]
}
