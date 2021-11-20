import React, { useEffect, useState } from 'react'
import Content from '../../components/content'
import { getProducts } from '../../pkg/smsApi/api'

interface ProductCardProps {
  children?: React.ReactNode
  product: Product
}
function ProductCard(props: ProductCardProps) {
  const { product: {name, suggestedPrice, stock} } = props
  return (
    <div>
      <h3>{name}</h3>
      <div style={{display: "flex", flexDirection: "row"}}>
        <div>
          <p>Price: </p>
          <p>Stock: </p>
        </div>
        <div>
          <div>
            <p>{suggestedPrice}</p>
            <p>{stock}</p>
          </div>
        </div>
      </div>
    </div>
  )
}

function Products() {

  const [ products, setProducts ] = useState<Product[]>([])


  const fetchProducts = async () => {
    getProducts().then(response => {
      if (response.status === 200) {
        setProducts(response.data)
      }
    })
  }

  useEffect(() => {
    fetchProducts().catch(err => {
      console.error(err)
    })
  })


  return <Content>


{products.map(product => <ProductCard product={product} key={product.id}/>)}

  </Content>
}

export default Products