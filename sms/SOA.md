---
marp: true
class: invert
author: vitor maia
---

# Consertos RUP

---

1. Em Criar Conta, o usuário deve informar os campos ( nome, email, nome de loja e senha.) e não um objeto. O objeto deve ser criado pela tela e passado para o controlador. 
![w:1000](./docs/consertos/1.png)

---

2. Em Efetuar Login, não há uma criação de usuário. A collection deve ter um método reflexivo (em vez de create, para procurar o usuário na collection
![w:1000](./docs/consertos/2.png)

---

3. Em Criar Venda, em vez de TransactionCollection fazer todo o fluxo de controle, isso deveria ser feito pelo TransactionControl. Correio deve ser uma fronteira e deve incluir o ator Correio.  Promover Item para Transaction (em vez de ficar em Sale e Purchase).

![w:1000](./docs/consertos/3.png)

---

# Projeto em SOA

## Especificação do Modelo de Negócio

---

## Modelo de Informações

![w:1000](./docs/biz-info.png)

---

## Modelo Navegacional

![w:800](./docs/nav.png)

---

## Prototipo de Interface Gráfica

<div style="display: flex; flex-direction: row; justify-content: space-between;">

![h:500](./docs/wireframes/1.jpg) ![h:500](./docs/wireframes/3.jpg) ![h:500](./docs/wireframes/2.jpg)

</div>

---

# Análise de Serviços

---

## Divisão em serviços

### Serviços stateful:
- Serviço de autenticação
- Serviço de transações
- Serviço de produtos
### Serviços stateless
- Serviço do webclient, com interfaces para gerenciamento de produtos e gerenciamento de transações
- Serviço de transporte/entrega

---

## Modelo de Interação dos Serviços
(AJUSTADO -- removidos os casos de uso relacionados a transporte)
![w:800](./docs/services-by-use-cases.png)


---

## Arquitetura de Serviços
(ERRADO)
![w:1000](./docs/arq-servicos.png)

---

## Arqitetura de Serviços
(AJUSTADO)
![w:700](./docs/arq-servicos2.png)

---

## Modelo de Informações Refinado
![w:1000](./docs/biz-info-refinado.png)

---

## Diagrama de Componentes

(AJUSTADO - Removido o serviço Stores, que ficará junto de Accounts)
![w:800](./docs/component-diagram.png)

---

## Implementação

<https://github.com/vitorcodesalittle/anps2021.1>

---

## Fim

Feliz natal e ano novo 💫
