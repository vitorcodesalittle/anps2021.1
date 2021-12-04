import React from 'react'
import { BrowserRouter as Router, Route, Routes} from 'react-router-dom'
import Auth from '../auth'
import Products from '../products'
import Transactions from '../transactions'
import NotImplemented from '../../components/not-implemented'
import {ProductsContextProvider} from '../../contexts/products'

export default function App() {
  return (
    <div>
      <ProductsContextProvider>
        <Router>
          <Routes>
            <Route path="/" element={
              <>
                <p>Stock management is fun with sms</p>
                <a href="/auth">Click here to create an account</a>
              </>
            }/>
            <Route path="/auth" element={<Auth/>}/>
            <Route path="/transactions" element={<Transactions/>}/>
            <Route path="/products" element={<Products/>}/>
            <Route path="/me" element={<NotImplemented/>}/>
          </Routes>
        </Router>
      </ProductsContextProvider>
    </div>
  )

}