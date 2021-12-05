package model.services.transporter

import model.transactions.Address

import scala.concurrent.Future

sealed case class Transporter(name: String)

object Transporter {
  def correios = Transporter("CORREIOS")
}

sealed trait TransporterApi {
  def getTransporters: Future[List[Transporter]]

  def getDeliveryCost(address: Address, method: Transporter): Future[Double]
}
