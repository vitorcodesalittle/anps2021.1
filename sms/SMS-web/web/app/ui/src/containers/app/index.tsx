import React from 'react'
import { BrowserRouter as Router, Route, Routes} from 'react-router-dom'
import Auth from '../auth'
import Products from '../products'
import Transactions from '../transactions'
import NotImplemented from '../../components/not-implemented'
export default function App() {
  return (
    <div>
      <Router>
        <Routes>
          <Route path="/auth" element={<Auth/>}/>
          <Route path="/transactions" element={<Transactions/>}/>
          <Route path="/products" element={<Products/>}/>
          <Route path="/me" element={<NotImplemented/>}/>
        </Routes>
      </Router>
    </div>
  )

}