import React, { useEffect, useState } from 'react'
import Content from '../../components/content'
import ProductCard from '../../components/product-catd'
import { Schema } from '../../pkg/form'
import Form from '../../components/form'
import { getProducts as apiGetProducts, createProduct as apiCreateProduct } from '../../pkg/smsApi/api'


const defaultProductData = (): ProductData => ({
  name: "",
  barcode: "",
  stock: 50,
  suggestedPrice: 10.00
})

function Products() {

  const [ products, setProducts ] = useState<Product[]>([])


  const getProducts = () => {
    return apiGetProducts().then(response => {
      if (response.status === 200) {
        console.log(response.data)
        setProducts(response.data)
      }
    })
  }

  const createProduct = (productData: ProductData) => {
    return apiCreateProduct(productData).then(response => {
      if (response.status === 200) {
        setProducts([ response.data, ...products ])
      }
    })
  }

  useEffect(() => {
    getProducts().catch(err => {
      console.error(err)
    })
  }, [])

  const ProductDataSchema: Schema<ProductData> = {
    name: {
      label: "Name",
      htmlType: "text",
      order: 0,
      onChange: (data, value) => ({...data, name: value}),
    },
    stock: {
      label: "Initial stock",
      htmlType: "text",
      onChange: (data, value) => ({...data, stock: parseInt(value, 10)}),
      order: 1
    },
    suggestedPrice: {
      label: "Price",
      htmlType: "number",
      numberStep: "0.1",
      order: 2,
      onChange: (data, value) => ({...data, suggestedPrice: parseInt(value, 10)})
    },
    barcode: {
      label: "Barcode",
      htmlType: "text",
      order: 3,
      onChange: (data, value) => ({...data, barcode: value})
    }
  }
  console.log(products)

  return (
    <Content>

      <h1> Products </h1>

      <div>
        <Form<ProductData> initialState={defaultProductData()} schema={ProductDataSchema} onSubmit={createProduct} submitLabel="Salvar produto"/>
      </div>
      
      {products.map(product => <ProductCard product={product} key={product.id}/>)}
    </Content>
  )
}

export default Products