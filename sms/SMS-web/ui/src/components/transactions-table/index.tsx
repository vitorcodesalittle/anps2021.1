import React from 'react'
import ItemsTable from '../items-table'
import 'antd/dist/antd.css'
import { Table, Descriptions, Typography } from 'antd'

const { Title } = Typography

interface TransactionsTableProps {
    transactions: ((Purchase) | (Sale))[]
}

const isSale = (thing: Sale | Purchase): thing is Sale => {
    return (thing as Sale).deliveryMethod !== undefined
}
type ifSale<T> = (arg: Sale | Purchase) => T
const ifSaleElse = <T, G>(saleFn: (value:Sale) => T, elseReturn: G) => {
    return (trnasaction: Sale | Purchase) => {
        if (isSale(trnasaction)) {
            return saleFn(trnasaction)
        } else {
            return elseReturn
        }
    }
}

const getItemsTotal = (items: Item[]) => items.reduce((acc, item) => acc + item.quantity * item.price, 0)

const TransactionsTable: React.FC<TransactionsTableProps> = function(props) {
    const {transactions} = props;

    const columns = [
        {
            title: "ID",
            render: (transaction: Transaction) => transaction.transactionId
        },
        {
            title: "Type",
            render: ifSaleElse(() => "SALE", "PURCHASE")
        },
        {
            title: "Delivery Method",
            render: ifSaleElse(sale => sale.deliveryMethod, "-")
        },
        {
            title: "Delivery Price",
            render: ifSaleElse(sale => sale.deliveryPrice, "-")
        },
        {
            title: "Sale Price",
            render: ifSaleElse(sale => getItemsTotal(sale.items), '-')
        },
        {
            title: "Total",
            render: (transaction: Sale | Purchase) => {
                if (isSale(transaction)) {
                    return transaction.deliveryPrice + getItemsTotal(transaction.items)
                } else {
                    return '-'
                }
            }
        }
    ]

    const renderInfo = (record: Purchase | Sale) => {
        if (isSale(record)) {
            return (
                <div style={{display: 'flex', flexDirection: 'row', justifyContent: 'space-evenly'}}>
                    <div>
                        <Descriptions title="Address">
                            <Descriptions.Item label="Country">{record.address.country}</Descriptions.Item>
                            <Descriptions.Item label="State">{record.address.state}</Descriptions.Item>
                            <Descriptions.Item label="City">{record.address.city}</Descriptions.Item>
                            <Descriptions.Item label="Street">{record.address.street}</Descriptions.Item>
                            <Descriptions.Item label="Extra">{record.address.extra}</Descriptions.Item>
                            <Descriptions.Item label="CEP">{record.address.zip}</Descriptions.Item>
                        </Descriptions>
                    </div>
                    <div style={{maxWidth: 500}}>
                            <Title level={5}>Order</Title>
                            <ItemsTable items={record.items}/>
                    </div>
                </div>
            )
        } else {

        }
    }

    return (
        <Table
            dataSource={transactions}
            columns={columns}
            pagination={false}
            size="small"
            rowKey={tr=>tr.transactionId}
            expandable={{
                expandedRowRender: (record) => renderInfo(record)
            }}
        />
    )
}

export default TransactionsTable