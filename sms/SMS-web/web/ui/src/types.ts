
type Store = {
  name: string;
}

type User = {
  id: number;
  name: string;
  stores: Store[];
  email: string;
  emailVerified: boolean;
}

type Product = {
  id: number;
  name: string;
  stock: number;
  suggestedPrice: number;
  barcode: string;
  storeId: number;
}

type ProductData = {
  name: string;
  suggestedPrice: number;
  stock: number;
  barcode: string;
}


type Item = {
  productId: number;
  quantity: number;
  price: number;
  product: Product;
}

type Transaction = {
  transactionId: number;
  createdAt: Date;
  items: Item[];
}

type DeliveryMethod = 'correios' | 'jadlog' | 'motoboy' | 'sedex'

type ItemData = {
  productId: number;
  quantity: number;
}

type Address = {
  country?: string;
  state?: string;
  city?: string;
  province?: string;
  cep?: string;
  extra?: string;
  street?: string;
}

type SaleData = {
  deliveryAddress: Address;
  deliveryMethod: DeliveryMethod;
  items: ItemData[];
}

type Sale = {
  transaction: Transaction;
  deliveryMethod: DeliveryMethod;
  deliveryPrice: number;
} & Transaction

type MountCashFlowRequest = {
  fromDate: Date;
  toDate: Date;
}

type Purchase = Transaction

type SignUpData = {
  email: string;
  name: string;
  password: string;
  storeName: string;
}

type LoginData = {
  email: string;
  password: string;
}
