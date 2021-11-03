package model.services.transporter

import model.global.Address

import scala.concurrent.Future

sealed trait DeliveryMethod

sealed case class Transporter(name: String)

object Transporter {
  def correios = Transporter("CORREIOS")
}

sealed trait TransporterApi {
  def getTransporters: Future[List[Transporter]]

  def getDeliveryCost(address: Address, method: DeliveryMethod): Future[Double]
}
