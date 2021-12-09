'use strict'
const correios = require('../../transporters/correios')

module.exports = async function (fastify, opts) {
  fastify.get('/correios', async function (request, reply) {
    const result = await correios.getPreco('51130320', '04870470')
    const correiosResponse = result[0]
    const { valor, prazoEntrega } = correiosResponse
    const price = parseFloat(valor.replace(/,/g, '.'))
    const deadline = new Date()
    deadline.setDate(deadline.getDate() + parseInt(prazoEntrega))
    const response = { price, deadline}
    return response
  })
}
