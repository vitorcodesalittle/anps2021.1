package model.transactions

import slick.jdbc.PostgresProfile.api._
import slick.lifted.ProvenShape

import java.time.Instant

class Sales(tag: Tag) extends Table[Sale](tag, "SALES") {
  def id: Rep[Int] = column[Int]("ID", O.PrimaryKey, O.AutoInc)

  def transactionId: Rep[TransactionId] = column[TransactionId]("TRANSACTION_ID")

  def storeId: Rep[Int] = column[Int]("STORE_ID")

  def createdAt: Rep[Instant] = column[Instant]("CREATED_AT")

  def deliveryPrice: Rep[Double] = column[Double]("DELIVERY_PRICE")

  def deliveryMethod: Rep[DeliveryMethod] = column[DeliveryMethod]("DELIVERY_METHOD")

  def addressCEP: Rep[String] = column[String]("ADDRESS_CEP")

  def addressStreet: Rep[String] = column[String]("ADDRESS_STREET")

  def addressProvince: Rep[String] = column[String]("ADDRESS_PROVINCE")

  def addressState: Rep[String] = column[String]("ADDRESS_STATE")

  def addressCountry: Rep[String] = column[String]("ADDRESS_COUNTRY")

  def addressExtra: Rep[String] = column[String]("ADDRESS_EXTRA")

  def addressCity: Rep[String] = column[String]("ADDRESS_CITY")

  def * : ProvenShape[Sale] = (id.?, transactionId, storeId, createdAt, deliveryMethod, deliveryPrice, (addressCEP, addressStreet, addressCity, addressState, addressCountry, addressExtra, addressProvince)).shaped <> (row => {
    val (id, transactionId, storeId, createdAt, deliveryMethod, deliveryPrice, (cep, street, state, city, country, extra, province)) = row
    val address = Address(country = country, state = state, city = city, street = street, province = province, extra = extra, cep = cep)
    Sale(transactionId = transactionId, id = id, storeId = storeId, createdAt = createdAt, deliveryMethod = deliveryMethod, deliveryPrice = deliveryPrice, deliveryAddress = address, items = None)
  }, (s: Sale) ⇒ {
    def f1(a: Address): (String, String, String, String, String, String, String) = {
      Address.unapply(a).get
    }
    Some((s.id, s.transactionId, s.storeId, s.createdAt, s.deliveryMethod, s.deliveryPrice, f1(s.deliveryAddress)))
  })

  lazy val transactions = TableQuery[Transactions]
}
