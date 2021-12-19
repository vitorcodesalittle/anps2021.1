import React, { useEffect, useState } from 'react'
import { Schema, SchemaProperty } from '../../pkg/form'
import Input from '../input'

interface FormProps<T extends Record<string, unknown>> { 
  schema: Schema<T>;
  submitLabel: string;
  onSubmit: (value: T) => any;
  initialState: T;
  children?: React.ReactElement;
  onCancel?: () => void;
  cancelLabel?: string
}
const Form = <T extends Record<string, unknown>> (props: FormProps<T>) => {
  const { schema, initialState, submitLabel, onSubmit, onCancel, cancelLabel} = props
  const [data, setData] = useState<T>(initialState)

  const nodes = Object.entries(schema).map(([key, value]) => {
    const valueAsProp = value as SchemaProperty<T, any, any, any>
    return [key, valueAsProp] as [string, SchemaProperty<T, any, any, any>]
  })
    .sort(([_, value]) => value.order)
    .map(([key, value]) => {
      if (value.render) return <value.render onChange={event => setData(value.onChange(data, event))}/>
      return <Input key={key} type={value.htmlType} label={value.label} onChange={event => setData(value.onChange(data, event.target.value))}/>
    })
  const handleSubmit = () => {
    onSubmit(data)
  }

  return (
    <form onSubmit={event => event.preventDefault()}>
      {nodes}
      <button onClick={() => handleSubmit()}>{submitLabel}</button>
      <button onClick={onCancel}>{cancelLabel || "Cancel"}</button>
    </form>
  )

}

export default Form
