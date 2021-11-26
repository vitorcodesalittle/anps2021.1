import React, { useEffect, useState } from 'react'
import { Schema, SchemaProperty } from '../../pkg/form'

interface FormProps<T extends Record<string, unknown>> { 
  schema: Schema<T>;
  submitLabel: string;
  onSubmit: (value: T) => any;
  initialState: T;
  children?: React.ReactElement;
}

interface InputProps {
  key:string;
  step?: string;
  label: string;
  type: string;
  onChange: React.ChangeEventHandler<HTMLInputElement>;
}

const Input = ({key, label, type, onChange, step}: InputProps) => {
  return (
    <div key={key}>
      <label>{label}</label>
      <input type={type} step={type === 'number' ? step || '1' : undefined} name={key} onChange={(event) => onChange(event)}></input>
    </div>
  )
}

const Form = <T extends Record<string, unknown>> (props: FormProps<T>) => {
  const { schema, initialState, submitLabel, onSubmit} = props
  const [data, setData] = useState<T>(initialState)

  const nodes = Object.entries(schema).map(([key, value]) => {
    const valueAsProp = value as SchemaProperty<T, any>
    return [key, valueAsProp] as [string, SchemaProperty<T, any>]
  })
    .sort(([_, value]) => value.order)
    .map(([key, value]) => {
      return <Input key={key} type={value.htmlType} label={value.label} onChange={event => setData(value.onChange(data, event.target.value))}/>
    })
  const handleSubmit: React.FormEventHandler<HTMLFormElement> = (event) => {
    event.preventDefault()
    onSubmit(data)
  }

  useEffect(() => {
    console.log('form changed:')
    console.log(data)
  }, [data])

  return (
    <form onSubmit={handleSubmit}>
      {nodes}
      <button>{submitLabel}</button>
    </form>
  )

}

export default Form
