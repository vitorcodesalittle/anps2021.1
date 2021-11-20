import React from 'react'
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
export default ProductCard