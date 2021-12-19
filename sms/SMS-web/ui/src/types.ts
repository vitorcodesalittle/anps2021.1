
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

type Transaction = {
  transactionId: number;
  createdAt: Date;
}

type DeliveryMethod = 'correios' | 'jadlog' | 'motoboy' | 'sedex'

type ItemData = {
  price: number;
  description: string;
  productId: number;
  quantity: number;
}
type Item = ItemData & {
  saleId: number;
}

type Address = {
  country?: string;
  state?: string;
  city?: string;
  street?: string;
  extra?: string;
  zip?: string;
}
type TransactionData = {
  storeId: number
}
type SaleData = {
  deliveryAddress: Address;
  deliveryMethod: DeliveryMethod;
  items: ItemData[];
} & TransactionData

type Sale = {
  deliveryMethod: DeliveryMethod;
  deliveryPrice: number;
  items: Item[];
  address: Address
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
