import axios from 'axios'

const api = axios.create({
  withCredentials: true,
  baseURL: 'http://localhost:9000/api'
})

export const login = (data: LoginData) => api.post('/login', data)
export const signUp = (data: SignUpData) => api.post('/sign-up', data)

export const getProducts = () => api.get('/products')

export const createProduct = (productData: ProductData) => api.post('/products', productData)

export const getTransactions = () => api.get('/transactions')

export const createSale = (saleData: SaleData) => api.post('/sales', saleData).then(response => {
  console.log('Finished request. Here is the response', response)
  return response
})

export const mountCashFlow = (request: MountCashFlowRequest) => {
  throw new Error('Not implemented')
}