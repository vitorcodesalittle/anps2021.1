import React, { useEffect, useState, useContext } from 'react'
import ProductsContext, { useProducts } from '../../contexts/products'
import ProductCard from '../../components/product-card'

interface SelectProps<T> {
    data: T[]
    onSelect: (thing: T) => any
}

interface SelectProductsProps extends SelectProps<Product> {}

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
    item: ItemData,
    products: Product[],
    onUpdate: (item: ItemData) => any
}

const ItemEdit: React.FC<ItemEditProps> = function (props) {
    const {products, item, onUpdate} = props
    const product = products.find(p => p.id === item.productId)
    const total = (product?.suggestedPrice || 0) * item.quantity
    return (
        <tr>
            <td>{product?.name}</td>
            <td>{item.quantity}</td>
            <td>{product?.suggestedPrice}</td>
            <td>{total}</td>
            <td>
                <button onClick={() => onUpdate({...item, quantity: item.quantity + 1})}>+</button>
                <button onClick={() => onUpdate({ ...item, quantity: item.quantity - 1})}>-</button>
            </td>
        </tr>
    )
}

function SelectItems() {
    const {getProducts, products, loading, loaded} =  useContext(ProductsContext)
    const [ items, setItems ] = useState<ItemData[]>([])

    useEffect(() => {
        if (!loaded && !loading) {
            getProducts(true).then(console.log).catch(err => console.error(err))
        }
    }, [loaded])

    const addItem = (productId: number, quantity: number) => {
        setItems([ {productId, quantity }, ...items ])
    }

    const handleItemUpdate = (item: ItemData) => {
        if (item.quantity <= 0) {
            setItems(
                items.filter(stateItem => stateItem.productId !== item.productId)
            )
        } else {
            setItems(
                items.map(stateItem => stateItem.productId === item.productId ? item : stateItem)
            )
        }
    }
    
    useEffect(() => {console.log(items)}, [items])

    return (
        <div>
            <SelectProducts
                data={products.filter(p => !items.find(item => item.productId === p.id ))}
                onSelect={product => addItem(product.id, 1)}/>
            <table>
                <thead>
                    <tr>
                        <th>
                            Name
                        </th>
                        <th>
                            Quantity
                        </th>
                        <th>
                            Price
                        </th>
                        <th>
                            Total
                        </th>
                    </tr>
            </thead>
            <tbody>
                {items.map(item => (
                    <ItemEdit item={item} products={products} key={item.productId} onUpdate={handleItemUpdate}/>
                ))}
            </tbody>
            </table>
        </div>
    )
}

export default SelectItems