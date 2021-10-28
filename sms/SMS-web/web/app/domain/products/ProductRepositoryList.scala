class ProductRepositoryList extends ProductRepository {

  def products: Seq[Product] = Seq()

  def getAll(): Future[Seq[Product]] = Future { products }
  def create(thing: Product): Future[Product] = 
  def exists(thing: Product): Future[Boolean] = ???
  def update(thing: Product): Future[Product] = ???
  def remove(thing: Product): Future[Int] = ???
}