import React from 'react'
import { Table } from 'antd'

interface ItemsTableProps {
    items: Item[]
}

const ItemsTable = (props: ItemsTableProps) => {
    const { items } = props
    const columns = [
        {
            title: "Product Description",
            render: (item: Item) => item.description
        },
        {
            title: "Quantity",
            render: (item: Item) => item.quantity,
        },
        {
            title: "Price",
            render: (item: Item) => item.price
        },
        {
            title: "Total",
            render: (item: Item) => item.price * item.quantity
        }
    ]
    return <Table dataSource={items} columns={columns} pagination={false} rowKey={item => `${item.productId}${item.saleId}`}/>
}

export default ItemsTable