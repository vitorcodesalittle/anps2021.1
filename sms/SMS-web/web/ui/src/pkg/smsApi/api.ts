import axios, { AxiosRequestTransformer } from 'axios'


const api = axios.create({
    baseURL: 'http://localhost:9000',
    withCredentials: true
})

export const login = (data: LoginData) => api.post('/login', data)
export const signUp = (data: SignUpData) => api.post('/sign-up', data)

export const getProducts = () => api.get('/products')