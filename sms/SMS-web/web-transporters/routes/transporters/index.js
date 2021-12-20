'use strict'
const correios = require('../../transporters/correios')


module.exports = async function (fastify, opts) {
	const handler = async function (request, reply) {
		console.log(request.query)
		const cep = request.query.cep
		const result = await correios.getPreco('51130320', cep)
		console.log({cep})
		const correiosResponse = result[0]
		const { valor, prazoEntrega } = correiosResponse
		const price = parseFloat(valor.replace(/,/g, '.'))
		const deadline = new Date()
		deadline.setDate(deadline.getDate() + parseInt(prazoEntrega))
		const response = { price, deadline}
		return response
	}
	return fastify.route({
		method: 'GET',
		url: '/correios',
		schema: {
			query: {
				cep: {
					type: "string"
				}
			}
		},
		handler
	})
}
