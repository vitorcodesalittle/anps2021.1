const frete = require('frete')
class Correios {
    constructor() {
        this.name = 'correios'
    }
    getPreco = async (
        cepOrigem,
        cepDestino,
        servico = frete.servicos.pac,
        peso = 1,
        formato = frete.formatos.caixaPacote,
        comprimento = 16,
        altura = 1,
        largura = 11,
        diametro = 1,
        maoPropria = 'N',
        valorDeclarado = 50,
        avisoRecebimento = 'N'
    ) =>
        new Promise((resolve, reject) => frete()
            .cepOrigem(cepOrigem)
            .peso(peso)
            .formato(formato)
            .comprimento(comprimento)
            .altura(altura)
            .largura(largura)
            .diametro(diametro)
            .maoPropria(maoPropria)
            .valorDeclarado(valorDeclarado)
            .avisoRecebimento(avisoRecebimento)
            .servico(servico)
            .precoPrazo(cepDestino, function(err, results) {
                if (err) {
                    return reject(err)
                }
                return resolve(results)
            }))
}

const correios = new Correios()

module.exports = correios
