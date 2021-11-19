import React from 'react'
import { Link } from 'react-router-dom'

function Navbar() {

  return <nav>
    <ul>
      <li>
        <Link to="/">Home</Link>
        <Link to="/transactions">Transactions</Link>
        <Link to="/products">Products</Link>
        <Link to="/me">Profile</Link>
        <button>Sair</button>
      </li>
    </ul>
  </nav>
}

export default Navbar