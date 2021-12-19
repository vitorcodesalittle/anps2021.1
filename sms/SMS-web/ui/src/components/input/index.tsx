import React from 'react'
import { Typography, Input } from 'antd'

const { Text } = Typography

interface InputProps {
  key?:string;
  step?: string;
  label: string;
  type: string;
  onChange: React.ChangeEventHandler<HTMLInputElement>;
}

const MyInput = ({key, label, type, onChange, step}: InputProps) => {
  return (
    <div key={key}>
      <Text>{label}: </Text>
      <Input type={type} step={type === 'number' ? step || '1' : undefined} name={key} onChange={(event) => onChange(event)}></Input>
    </div>
  )
}

export default MyInput