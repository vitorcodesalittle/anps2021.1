'use strict'
const correios = require('../../transporters/correios')

module.exports = async function (fastify, opts) {
  fastify.get('/correios', async function (request, reply) {
    const response = await correios.getPreco('51130320', '04870470')
    return response
  })
}
