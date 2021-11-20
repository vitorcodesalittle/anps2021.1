
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

type Item = {
  productId: number;
  quantity: number;
  price: number;
  product: Product;
}

type Transaction = {
  id: number;
  createdAt: Date;
  items: Item[];
}

type DeliveryMethod = 'correios' | 'jadlog' | 'motoboy' | 'sedex'

type Sale = {
  transaction: Transaction;
  deliveryMethod: DeliveryMethod;
  deliveryPrice: number;
}



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