import React, {createContext,  useState } from 'react'
import {getProducts as apiGetProducts} from '../../pkg/smsApi'

interface IProductContext {
    products: Product[];
    loading: boolean;
    loaded: boolean;
    getProducts: (force: boolean) => Promise<Product[]>;
}

const ProductsContext = createContext<IProductContext>({
  products: [],
  loading: false,
  loaded: false,
  getProducts: async boolean => []
})

const ProductsContextProvider: React.FC = function(props) {
  const [ products, setProducts ] = useState<Product[]>([])
  const [loaded, setLoaded ] = useState(false)
  const [ loading, setLoading ] = useState(false)

  const getProducts = async (force = false): Promise<Product[]> => {
    console.log({
      products,
      loaded,
      loading
    })
    if (!loading && (!products.length || force)) {
      setLoading(true)
      return apiGetProducts().then(response => {
        setLoading(false)
        setLoaded(true)
        if (response.status === 200) {
          setProducts(response.data)
          return response.data
        } else {
          return Promise.reject(response)
        }
      })
    } else {
      return products
    }
  }

  return (
    <ProductsContext.Provider value={{products, loading, getProducts, loaded}}>
      {props.children}
    </ProductsContext.Provider>
  )
}

export { ProductsContextProvider }
export default ProductsContext

