import React from 'react'
interface ProductCardProps {
  children?: React.ReactNode
  product: Product
}
const ProductCard: React.FC<ProductCardProps> = function (props) {
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
      {props.children}
    </div>
  )
}
export default ProductCard