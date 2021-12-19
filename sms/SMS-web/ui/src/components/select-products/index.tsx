import React, { useEffect, useState, useContext } from 'react'
import { Input, message, Typography, Button } from 'antd'
import ProductsContext from '../../contexts/products'
import ProductCard from '../../components/product-card'

const {Text} = Typography

interface SelectProps<T> {
    data: T[];
    onSelect: (thing: T) => void;
}

type SelectProductsProps = SelectProps<Product>

function SelectProducts(props: SelectProductsProps) {
  const {data: products, onSelect} = props
  const handleSelect = (product: Product) => onSelect(product)
  return (
    <div>
      {products.map(p => <ProductCard product={p} key={p.id}>
        <button onClick={() => handleSelect(p)}>Adicionar</button>
      </ProductCard>)}
    </div>
  )
}

interface ItemEditProps {
    item: ItemData;
    productDescription: string;
    onUpdate: (item: ItemData) => any;
}

const ItemEdit: React.FC<ItemEditProps> = function (props) {
  const {item, onUpdate, productDescription} = props
  const total = (item.price || 0) * item.quantity
  return (
    <div>
      <div>
        <Text>Product: </Text>
        <Text>{productDescription}</Text>
      </div>
      <div>
        <Text>Quantity: </Text><Input type="number" value={item.quantity} onChange={event => onUpdate({...item, quantity: parseInt(event.target.value)})} step="1"></Input>
      </div>
      <div>
        <Text>Price: </Text><Input type="number" value={item.price} onChange={event => onUpdate({...item, price: parseFloat(event.target.value)})} step="0.1"></Input>
      </div>
      <div>
        <Text>Total: </Text><Text>{total.toFixed(2)}</Text>
      </div>
      <div>
        <Button onClick={() => onUpdate({...item, quantity: item.quantity + 1})}>+</Button>
        <Button onClick={() => onUpdate({ ...item, quantity: item.quantity - 1})}>-</Button>
      </div>
    </div>
  )
}

interface SelectItemsProps {
  onChange: (items: ItemData[]) => void;
}
function SelectItems(props: SelectItemsProps) {
  const [products, setProducts] = useState<string[]>([])
  const [items, setItems] = useState<ItemData[]>([])
  const [productDescription, setProductDescription] = useState('')
  const handleAddProduct = (productDescription: string) => {
    if (!productDescription) {
      return
    }
    if (products?.includes(productDescription)) {
      message.info("this product was already added")
      return
    }
    setProducts([productDescription, ...products])
    setItems([{description: productDescription, price: 1.00, productId: Math.floor(Math.random() * 99999), quantity: 1}, ...items])
    setProductDescription('')
  }

  const handleUpdate = (updated: ItemData) => {
    setItems(items.map(item => item.productId === updated.productId ? updated : item))
  }

  useEffect(() => {
    props.onChange(items)
  }, [items])
  return (
    <div>
      {
        items.map(item => <ItemEdit item={item} productDescription={item.description} onUpdate={handleUpdate}/>)
      }
      <Text>Product</Text>
      <Input value={productDescription} onPressEnter={event => {
        event.preventDefault();
        handleAddProduct(productDescription)
      }} onChange={event => setProductDescription(event.target.value)}></Input>
    </div>
  )
}

export default SelectItems