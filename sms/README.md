# Stock Management System
No contexto de uma loja genérica, queremos criar uma aplicação que permita.
- Gerenciar produtos que são vendidos
- Gerenciar matéria prima que é comprada
- Salvar e emitir notas fiscais
- Visualizar o fluxo de caixa do negócio

## Casos de Uso:

- *Login como admin*
Pré condição:
Nenhuma

Pós condição:
Uma nova sessão é criada para o usuário

- Fluxo de eventos principal
1. O usuário preenche suas informações de login: email e senha
2. O backend verificar se o email corresponde ao de um administrador, e a senha bate com a registrada originalmente
3. Se usuário é autenticado, ele é redirecionado ao dashboard

Fluxo de eventos secundário:
4. Caso a autenticação falhe, o usuário recebe um feedback "Email ou senha estão errados".

- *Gerenciar produtos a venda*
	- Registrar um produto
Pré condição:
O usuário deve estar logado

Pós Condição
Um produto é persistido na aplicação

Fluxo de eventos principal
1. O usuário preenche informações sobre o produto: Nome, categorias, preço, quantidade inicial
2. O sistema valida as informações, impedindo o cadastro de nomes com caracteres especiais, produtos com nomes repetidos e produtos com informações incompletas.
3. Sendo um produto válido, ele é persistido.
4. O usuário recebe o feedback de que o produto foi corretamente registrado.

Fluxos secundários
5. Caso não seja um produto válido, é retornado uma mensagem ao usuário informando a razão da invalidez.

	- Atualizar um produto
Pré condição:
O usuário deve estar logado

Pós condição
Os dados do produto são atualizados.

Fluxo de eventos principal
1. O usuário atualiza as informações que desejar
2. O backend valida a informação, impedindo o cadastro de nomes com caracteres especiais, ou produtos com nomes repetidos.
3. Sendo uma atualização válida, o backend salva a alteração.
4. O usuário recebe o feedback de que o produto foi atualizado

Fluxos secundários
5. Caso não seja um produto válido, é retornado uma mensagem ao usuário informando a razão da invalidez.

- *Atualizar estoque*
- *Registrar vendas*
- *Cadastrar matéria-prima*
- *Cadastrar compra de matéria prima*
- *Visualizar fluxo de caixa*
- *Gerenciar categorias de produto.*
