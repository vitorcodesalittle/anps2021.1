# Mapemanto de Classes de Análise para Elementos de Project

| Classes de Análise    | Elementos de Projeto                                                      | Reason                                        |
|-----------------------|---------------------------------------------------------------------------|-----------------------------------------------|
| EncryptionService     | EncryptionService                                                         |                                               | 1
| SessionService        | SessionService, UserAction, UserRequest, UserInfo                         |UserRequest extende uma request normal na medida de 1 UserInfo, decodificado da sessão em uma UserAction (uma ação que precisa de autenticação | 5
| Correios              | TransporterService, TransporterInterface                                  |abstrai a lógica de cálculo de frete           | 7
| UserControl           | UserControl, UserController                                               |                                               | 9
| User                  | User                                                                      |                                               | 10
| UserCollection        | UserRepository, UserRepositoryRDB, Users                  |Users representa a tabela USERS (o msm vale para Products, Stores, ...)| 13
| StoreControl          | StoreControl                                                              |                                               | 14
| Store                 | Store                                                                     |                                               | 15
| StoreCollection       | StoreRepository, StoreRepositoryRDB, Stores              |                                               | 18
| Product               | Product                                                                   |                                               | 19
| ProductControl        | ProductControl, ProductController                                         |                                               | 21
| ProductCollection     | ProductRepository, ProductRepositoryRDB, Products      |                                               | 24
| Sale                  | Sale, Sales, SaleData                                                     |                                               | 25
| Transaction           | Transaction, Transactions, TransactionData                                |                                               | 26
| Address               | Address, AddressData                                                       |                                               | 27
| AuthScreen            | AuthScreen, LoginData, SignUpData, StoreData                              | as classes data representam a informação de foms da tela| 27
| TransactionScreen     | TransactionScreen                                                         |                                               | 28
| ProductScreen         | ProductScreen, ProductData                                                |                                               | 29
| TransactionCollection | TransactionRepository, TransactionRepositoryRDB    |                                               | 32
| Boundary              |                                                                           |                                               | 33
| Purchase              | Purchase, Purchases                                                       |                                               | 34
| Item                  | Item, Items                                                               |                                               | 35
 

## 0 - Padrão Arquitetural MVC
As seguintes classes foram adicionados para que o projeto siga um padrão MVC:
- Classes Controller
Responsáveis pela interação entre as views (páginas HTML) com o model (o modelo de negócio)
- Classes Repository
Responsáveis pela persistência das entidades
- UserAction
Especificamente porque uso Play como framework, a classe UserAction extende uma Action
do play com a decodificação de um token JWTo forma de mecanismo de reforçar o controle de acesso.

