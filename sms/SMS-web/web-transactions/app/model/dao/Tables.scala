package model.dao
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.jdbc.PostgresProfile
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.jdbc.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Items.schema ++ Purchases.schema ++ Sales.schema ++ Transactions.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Items
   *  @param productId Database column PRODUCT_ID SqlType(int4)
   *  @param quantity Database column QUANTITY SqlType(int4), Default(1)
   *  @param description Database column DESCRIPTION SqlType(text), Default(None)
   *  @param saleId Database column SALE_ID SqlType(int4)
   *  @param price Database column PRICE SqlType(float8) */
  case class ItemsRow(productId: Int, quantity: Int = 1, description: Option[String] = None, saleId: Int, price: Double)
  /** GetResult implicit for fetching ItemsRow objects using plain SQL queries */
  implicit def GetResultItemsRow(implicit e0: GR[Int], e1: GR[Option[String]], e2: GR[Double]): GR[ItemsRow] = GR{
    prs => import prs._
    val r = (<<[Int], <<[Int], <<?[String], <<[Int], <<[Double])
    import r._
    ItemsRow.tupled((_1, _2, _3, _4, _5)) // putting AutoInc last
  }
  /** Table description of table ITEMS. Objects of this class serve as prototypes for rows in queries. */
  class Items(_tableTag: Tag) extends profile.api.Table[ItemsRow](_tableTag, "ITEMS") {
    def * = (productId, quantity, description, saleId, price) <> (ItemsRow.tupled, ItemsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(productId), Rep.Some(quantity), description, Rep.Some(saleId), Rep.Some(price))).shaped.<>({r=>import r._; _1.map(_=> ItemsRow.tupled((_1.get, _2.get, _3, _4.get, _5.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column PRODUCT_ID SqlType(int4) */
    val productId: Rep[Int] = column[Int]("PRODUCT_ID")
    /** Database column QUANTITY SqlType(int4), Default(1) */
    val quantity: Rep[Int] = column[Int]("QUANTITY", O.Default(1))
    /** Database column DESCRIPTION SqlType(text), Default(None) */
    val description: Rep[Option[String]] = column[Option[String]]("DESCRIPTION", O.Default(None))
    /** Database column SALE_ID SqlType(int4) */
    val saleId: Rep[Int] = column[Int]("SALE_ID")
    /** Database column PRICE SqlType(float8) */
    val price: Rep[Double] = column[Double]("PRICE")

    /** Primary key of Items (database name ITEMS_pkey) */
    val pk = primaryKey("ITEMS_pkey", (saleId, productId))

    /** Foreign key referencing Sales (database name ITEMS_SALE_ID_fkey) */
    lazy val salesFk = foreignKey("ITEMS_SALE_ID_fkey", saleId, Sales)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Items */
  lazy val Items = new TableQuery(tag => new Items(tag))

  /** Entity class storing rows of table Purchases
   *  @param transactionId Database column TRANSACTION_ID SqlType(int4)
   *  @param description Database column DESCRIPTION SqlType(text), Default(None)
   *  @param id Database column ID SqlType(int4), AutoInc, PrimaryKey */
  case class PurchasesRow(transactionId: Int, description: Option[String] = None, id: Option[Int] = None)
  /** GetResult implicit for fetching PurchasesRow objects using plain SQL queries */
  implicit def GetResultPurchasesRow(implicit e0: GR[Int], e1: GR[Option[String]], e2: GR[Option[Int]]): GR[PurchasesRow] = GR{
    prs => import prs._
    val r = (<<?[Int], <<[Int], <<?[String])
    import r._
    PurchasesRow.tupled((_2, _3, _1)) // putting AutoInc last
  }
  /** Table description of table PURCHASES. Objects of this class serve as prototypes for rows in queries. */
  class Purchases(_tableTag: Tag) extends profile.api.Table[PurchasesRow](_tableTag, "PURCHASES") {
    def * = (transactionId, description, Rep.Some(id)) <> (PurchasesRow.tupled, PurchasesRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(transactionId), description, Rep.Some(id))).shaped.<>({r=>import r._; _1.map(_=> PurchasesRow.tupled((_1.get, _2, _3)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column TRANSACTION_ID SqlType(int4) */
    val transactionId: Rep[Int] = column[Int]("TRANSACTION_ID")
    /** Database column DESCRIPTION SqlType(text), Default(None) */
    val description: Rep[Option[String]] = column[Option[String]]("DESCRIPTION", O.Default(None))
    /** Database column ID SqlType(int4), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("ID", O.AutoInc, O.PrimaryKey)

    /** Foreign key referencing Transactions (database name PURCHASES_TRANSACTION_ID_fkey) */
    lazy val transactionsFk = foreignKey("PURCHASES_TRANSACTION_ID_fkey", transactionId, Transactions)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Purchases */
  lazy val Purchases = new TableQuery(tag => new Purchases(tag))

  /** Entity class storing rows of table Sales
   *  @param deliveryMethod Database column DELIVERY_METHOD SqlType(varchar), Length(256,true)
   *  @param deliveryPrice Database column DELIVERY_PRICE SqlType(float8)
   *  @param transactionId Database column TRANSACTION_ID SqlType(int4)
   *  @param addressCountry Database column ADDRESS_COUNTRY SqlType(text), Default(None)
   *  @param addressState Database column ADDRESS_STATE SqlType(text), Default(None)
   *  @param addressStreet Database column ADDRESS_STREET SqlType(text), Default(None)
   *  @param addressExtra Database column ADDRESS_EXTRA SqlType(text), Default(None)
   *  @param addressZip Database column ADDRESS_ZIP SqlType(text)
   *  @param addressCity Database column ADDRESS_CITY SqlType(text), Default(None)
   *  @param id Database column ID SqlType(int4), AutoInc, PrimaryKey */
  case class SalesRow(deliveryMethod: String, deliveryPrice: Double, transactionId: Int, addressCountry: Option[String] = None, addressState: Option[String] = None, addressStreet: Option[String] = None, addressExtra: Option[String] = None, addressZip: String, addressCity: Option[String] = None, id: Option[Int] = None)
  /** GetResult implicit for fetching SalesRow objects using plain SQL queries */
  implicit def GetResultSalesRow(implicit e0: GR[String], e1: GR[Double], e2: GR[Int], e3: GR[Option[String]], e4: GR[Option[Int]]): GR[SalesRow] = GR{
    prs => import prs._
    val r = (<<[String], <<[Double], <<[Int], <<?[Int], <<?[String], <<?[String], <<?[String], <<?[String], <<[String], <<?[String])
    import r._
    SalesRow.tupled((_1, _2, _3, _5, _6, _7, _8, _9, _10, _4)) // putting AutoInc last
  }
  /** Table description of table SALES. Objects of this class serve as prototypes for rows in queries. */
  class Sales(_tableTag: Tag) extends profile.api.Table[SalesRow](_tableTag, "SALES") {
    def * = (deliveryMethod, deliveryPrice, transactionId, addressCountry, addressState, addressStreet, addressExtra, addressZip, addressCity, Rep.Some(id)) <> (SalesRow.tupled, SalesRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(deliveryMethod), Rep.Some(deliveryPrice), Rep.Some(transactionId), addressCountry, addressState, addressStreet, addressExtra, Rep.Some(addressZip), addressCity, Rep.Some(id))).shaped.<>({r=>import r._; _1.map(_=> SalesRow.tupled((_1.get, _2.get, _3.get, _4, _5, _6, _7, _8.get, _9, _10)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column DELIVERY_METHOD SqlType(varchar), Length(256,true) */
    val deliveryMethod: Rep[String] = column[String]("DELIVERY_METHOD", O.Length(256,varying=true))
    /** Database column DELIVERY_PRICE SqlType(float8) */
    val deliveryPrice: Rep[Double] = column[Double]("DELIVERY_PRICE")
    /** Database column TRANSACTION_ID SqlType(int4) */
    val transactionId: Rep[Int] = column[Int]("TRANSACTION_ID")
    /** Database column ADDRESS_COUNTRY SqlType(text), Default(None) */
    val addressCountry: Rep[Option[String]] = column[Option[String]]("ADDRESS_COUNTRY", O.Default(None))
    /** Database column ADDRESS_STATE SqlType(text), Default(None) */
    val addressState: Rep[Option[String]] = column[Option[String]]("ADDRESS_STATE", O.Default(None))
    /** Database column ADDRESS_STREET SqlType(text), Default(None) */
    val addressStreet: Rep[Option[String]] = column[Option[String]]("ADDRESS_STREET", O.Default(None))
    /** Database column ADDRESS_EXTRA SqlType(text), Default(None) */
    val addressExtra: Rep[Option[String]] = column[Option[String]]("ADDRESS_EXTRA", O.Default(None))
    /** Database column ADDRESS_ZIP SqlType(text) */
    val addressZip: Rep[String] = column[String]("ADDRESS_ZIP")
    /** Database column ADDRESS_CITY SqlType(text), Default(None) */
    val addressCity: Rep[Option[String]] = column[Option[String]]("ADDRESS_CITY", O.Default(None))
    /** Database column ID SqlType(int4), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("ID", O.AutoInc, O.PrimaryKey)

    /** Foreign key referencing Transactions (database name SALES_TRANSACTION_ID_fkey) */
    lazy val transactionsFk = foreignKey("SALES_TRANSACTION_ID_fkey", transactionId, Transactions)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Sales */
  lazy val Sales = new TableQuery(tag => new Sales(tag))

  /** Entity class storing rows of table Transactions
   *  @param createdAt Database column CREATED_AT SqlType(timestamp)
   *  @param storeId Database column STORE_ID SqlType(int4), Default(1)
   *  @param id Database column ID SqlType(int4), AutoInc, PrimaryKey */
  case class TransactionsRow(createdAt: java.sql.Timestamp, storeId: Int = 1, id: Option[Int] = None)
  /** GetResult implicit for fetching TransactionsRow objects using plain SQL queries */
  implicit def GetResultTransactionsRow(implicit e0: GR[java.sql.Timestamp], e1: GR[Int], e2: GR[Option[Int]]): GR[TransactionsRow] = GR{
    prs => import prs._
    val r = (<<?[Int], <<[java.sql.Timestamp], <<[Int])
    import r._
    TransactionsRow.tupled((_2, _3, _1)) // putting AutoInc last
  }
  /** Table description of table TRANSACTIONS. Objects of this class serve as prototypes for rows in queries. */
  class Transactions(_tableTag: Tag) extends profile.api.Table[TransactionsRow](_tableTag, "TRANSACTIONS") {
    def * = (createdAt, storeId, Rep.Some(id)) <> (TransactionsRow.tupled, TransactionsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(createdAt), Rep.Some(storeId), Rep.Some(id))).shaped.<>({r=>import r._; _1.map(_=> TransactionsRow.tupled((_1.get, _2.get, _3)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column CREATED_AT SqlType(timestamp) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("CREATED_AT")
    /** Database column STORE_ID SqlType(int4), Default(1) */
    val storeId: Rep[Int] = column[Int]("STORE_ID", O.Default(1))
    /** Database column ID SqlType(int4), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("ID", O.AutoInc, O.PrimaryKey)
  }
  /** Collection-like TableQuery object for table Transactions */
  lazy val Transactions = new TableQuery(tag => new Transactions(tag))
}
