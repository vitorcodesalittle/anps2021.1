import React from 'react'
import { BrowserRouter as Router, Route} from 'react-router-dom'
import Content from '../../components/content'
import Auth from '../auth'
import Products from '../products'
import Transactions from '../transactions'
export default function App() {


  return (
    <div>
      <Content>
        <Router>
          <Route path="/auth"><Auth/></Route>
          <Route path="/transactions"><Transactions/></Route>
          <Route path="/products"><Products/></Route>
        </Router>
      </Content>
    </div>
  )

}