# [WIP] Mapemanto de Classes de Análise para Elementos de Project
| Classes de Análise | Elementos de Projeto | Reason |
|--------------------|----------------------|--------|
| User               | Store, User          | [1](./TabelaClassesAnaliseClassesProjeto#1)       |
| UserCollection     | UserRepository, UserRepositoryRDB, StoreRepository, StoreRepositoryRDB||
| ProductCollection  | ProductRepository, ProductRepositoryRDB ||
| TransactionCollection | TransactionRepository, TransactionRepositoryRDB ||

## 1
Decidi quebrar a classe de User em User e Store pensando que no futuro, um usuário poderia ter várias lojas, e uma loja poderia ser gerenciada por vários usuários.
