import axios from 'axios'

const api = axios.create({
    withCredentials: true,
    baseURL: "/api"
})

export const login = (data: LoginData) => api.post('/login', data)
export const signUp = (data: SignUpData) => api.post('/sign-up', data)

export const getProducts = () => api.get('/products')