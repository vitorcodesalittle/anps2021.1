import React, { useEffect, useState }from 'react'
import {Modal, Typography, Button, message, Select} from 'antd'
import {getTransactions as apiGetTransactions, createSale} from '../../pkg/smsApi'
import Content from '../../components/content'
import TransactionsTable from '../../components/transactions-table'
import Form from '../../components/form'
import SelectItems from '../../components/select-products'
import { Schema } from '../../pkg/form'
import AddressInput, { defaultAddress } from '../../components/address-input'
const { Text } = Typography

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
  address: defaultAddress(),
  storeId: 1,
  itemsData: []
})

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
    render: (props: {onChange: (deliveryMethod: string) => void}) => <> <Text>Delivery Method:</Text><Select style={{minWidth: 200}}>
      <Select.Option value="correios">Correios</Select.Option>
      <Select.Option value="sedex">Sedex</Select.Option>
    </Select></>,
    onChange: (data, value) => ({...data, deliveryMethod: value as string})
  },
  address: {
    render: (props: {onChange: (address: Address) => void}) => <AddressInput onChange={props.onChange}/>,
    label: 'Endereço',
    onChange: (data, value) => ({...data, address: value}),
    order: 1
  },
  itemsData: {
    htmlType: 'text',
    label: 'Iten',
    order: 2,
    render: (props: {onChange: (items: ItemData[]) => void}) => 
      <SelectItems onChange={items => props.onChange(items)}/>,
    onChange: (data, value) => ({ ...data, itemsData: value })
  }
}
function Transactions() {
  const [creatingSale, setCreatingSale] = useState(false)
  const [ transactions, setTransactions ] = useState<(Sale | Purchase)[]>([])
  const [ flowRequest,  ] = useState<MountCashFlowRequest>(defaultFlowRequest())
  const postSale = (saleData: SaleData) => {
    if (saleData.itemsData.length === 0) {
      message.warning("Put some products in the sale")
      return
    }
    console.log(saleData)
    return createSale(saleData).then((response) => {
      if (response.status === 200) {
        message.success({
          message: "Sale created"
        })
        setCreatingSale(false)
        setTransactions([...transactions, response.data])
      } else {
        message.error('Something wrong happened while registering sale')
        console.error(response)
      }
    }).catch((err) => {
      console.error(err)
      message.error({
        message: "Failed to create sale." + (process.env.NODE_ENV !== "production") ? " CHECK CONSOLE" : ""
      })
    })
  } 

  const getTransactions = (flowRequest: MountCashFlowRequest) => apiGetTransactions()

  useEffect(() => {
    getTransactions(flowRequest).then(transactions => {
      console.log(transactions)
      setTransactions(transactions.data)
    })
  }, [])

  return <Content>
    <Button onClick={() => setCreatingSale(true)}>Register Sale</Button>
    {/* <Form<MountCashFlowRequest> schema={FlowRequestSchema}
      onSubmit={getTransactions}
      initialState={flowRequest}
      submitLabel="Pegar Transações"/> */}
    <Modal title="Register a sale" onCancel={() => setCreatingSale(false)} footer={null} visible={creatingSale}>
      <Form<SaleData> schema={SaleDataSchema}
        onSubmit={postSale}
        initialState={defaultSaleData()}
        submitLabel="Criar venda"/>
    </Modal>
    <div>
      <TransactionsTable transactions={transactions}/>
    </div>
  </Content>
}
export default Transactions