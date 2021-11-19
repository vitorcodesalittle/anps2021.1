import React from 'react'

function Navbar() {

  return <nav>
    <ul>
      <li>
        <a href="/">Home</a>
        <a href="/transactions">Transactions</a>
        <a href="/products">Products</a>
        <a href="/me">Profile</a>
        <button>Sair</button>
      </li>
    </ul>
  </nav>
}

export default Navbar