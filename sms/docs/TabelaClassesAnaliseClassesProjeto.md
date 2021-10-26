# [WIP] Mapemanto de Classes de Análise para Elementos de Project
| Classes de Análise | Elementos de Projeto | Reason |
|--------------------|----------------------|--------|
| User               | Store, User          | [1](./TabelaClassesAnaliseClassesProjeto.md#1)       |
| UserController     | UserController, StoreController| [1] |
| UserCollection     | UserCollection, UserRepository, UserRepositoryRDB, StoreRepository, StoreRepositoryRDB||
| ProductCollection  | ProductCollection, ProductRepository, ProductRepositoryRDB ||
| TransactionCollection | TransactionCollection, TransactionRepository, TransactionRepositoryRDB ||
| Sale                  | Sale, Address     | [2](.TabelaClassesAnaliseClassesProjeto.md#2)|


## 1
Decidi quebrar a classe de User em User e Store pensando que no futuro, um usuário poderia ter várias lojas, e uma loja poderia ser gerenciada por vários usuários.

## 2
A informação de endereço parece ser reutilizável em outros contextos.
