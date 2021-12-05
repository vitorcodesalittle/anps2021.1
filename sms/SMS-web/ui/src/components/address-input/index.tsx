import React, { useState } from 'react'
import Input from '../input'

interface AddressInputProps {
  onChange: (address: Address) => void;
}

export const defaultAddress = (): Address => ({
  country: '',
  city: '',
  cep: '',
  extra: '',
  province: '',
  state: '',
  street: ''
})
function AddressInput(props: AddressInputProps) {
  const {onChange}=props
  const [address, setAddress] = useState<Address>(defaultAddress())
  const changeHandler = (k: keyof Address) => (event: React.ChangeEvent<HTMLInputElement>) => {
    const newAddress =  {...address, [k]: event.target.value}
    setAddress(newAddress)
    onChange(newAddress)
  }
  return (
    <>
      <Input label={'País'} type="text" onChange={changeHandler('country')}/>
      <Input label={'Cidade'} type="text" onChange={changeHandler('city')}/>
      <Input label={'CEP'} type="text" onChange={changeHandler('cep')}/>
      <Input label={'Estado'} type="text" onChange={changeHandler('state')}/>
      <Input label={'Province'} type="text" onChange={changeHandler('province')}/>
      <Input label={'Rua'} type="text" onChange={changeHandler('street')}/>
      <Input label={'Extra'} type="text" onChange={changeHandler('extra')}/>

    </>
  )
}

export default AddressInput