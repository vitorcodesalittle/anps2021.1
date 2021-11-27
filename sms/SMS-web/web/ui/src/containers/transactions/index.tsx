import React, { useEffect, useState, ReactEventHandler}from 'react'
import {getTransactions as apiGetTransactions, createSale as apiCreateSale} from '../../pkg/smsApi'
import Content from '../../components/content'
import TransactionCard from '../../components/transaction-card'
import Form from '../../components/form'
import SelectItems from '../../components/select-products'
import Input from '../../components/input'
import { Schema } from '../../pkg/form'

const defaultFlowRequest = (): MountCashFlowRequest => {
  const now = new Date()
  const startOfMonth = new Date(now.getFullYear(), now.getMonth(), 0)
  return {
    fromDate: startOfMonth,
    toDate: now
  }
}
const defaultSaleData = (): SaleData => ({
  deliveryMethod: 'correios',
  items: []
})
function Transactions() {
  const [ transactions,  ] = useState<(Sale | Purchase)[]>([])
  const [ flowRequest,  ] = useState<MountCashFlowRequest>(defaultFlowRequest())
  const createSale = (saleData: SaleData) => {
    console.log('CREATING SALE NOW - AKA SUBMITING FORM')
    apiCreateSale(saleData).then(console.log).catch(console.error)
  } 
  const getTransactions = (flowRequest: MountCashFlowRequest) => apiGetTransactions()

  useEffect(() => {
    // getTransactions(flowRequest).then(console.log).catch(console.error)
  }, [])

  const FlowRequestSchema: Schema<MountCashFlowRequest> = {
    fromDate: {
      htmlType: 'date',
      label: 'Desde',
      order: 0,
      onChange: (data, value) => ({ ...data, fromDate: new Date(value) }),
    },
    toDate: {
      htmlType: 'date',
      label: 'Até',
      order: 1,
      onChange: (data, value) => ({...data, toDate: new Date(value)})
    }
  }
  const SaleDataSchema: Schema<SaleData> = {
    deliveryMethod: {
      htmlType: 'text',
      label: 'Método de entrega',
      order: 0,
      render: Input,
      onChange: (data, value) => ({...data, deliveryMethod: value as DeliveryMethod})
    },
    items: {
      htmlType: 'text',
      label: 'Iten',
      order: 1,
      render: (props: {onChange: (items: ItemData[]) => void}) => 
        <SelectItems onChange={items => props.onChange(items)}/>,
      onChange: (data, value) => ({ ...data, items: value })
    }
  }
  return <Content>
    <h2>Get report</h2>
    <Form<MountCashFlowRequest> schema={FlowRequestSchema} onSubmit={getTransactions} initialState={flowRequest} submitLabel="Pegar Transações"/>
    <h2>Create sale</h2>
    <Form<SaleData> schema={SaleDataSchema} onSubmit={createSale} initialState={defaultSaleData()} submitLabel="Criar venda"/>
    {transactions.map(transaction => <TransactionCard key={transaction.transactionId} transaction={transaction}/>)}
  </Content>
}
export default Transactions