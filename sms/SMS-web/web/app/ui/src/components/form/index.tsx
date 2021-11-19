import React, { useState } from 'react'
import { Schema, SchemaProperty } from '../../pkg/form'

interface FormProps<T extends {}> { schema: Schema<T>, submitLabel: string, onSubmit: (value: T) => any, initialState: T, children?: React.ReactElement }

const Form = <T extends {},> (props: FormProps<T>) => {
    const { schema, initialState, submitLabel, onSubmit} = props;
    const [data, setData] = useState<T>(initialState)

  const changeData  = (partial: Partial<T>) => setData({...data, ...partial})
    const nodes = Object.entries(schema).map(([key, value]) => {
        const valueAsProp = value as SchemaProperty<T, any>
        return [key, valueAsProp] as [string, SchemaProperty<T, any>]
    })
    .sort(([_, value]) => value.order)
    .map(([key, value]) => {
        return (
            <div key={key}>
                <label>{value.label}</label>
                <input type={value.htmlType} name={key} onChange={(event) => changeData(value.onChange(data, event.target.value))}></input>
            </div>
        )
    })
    const handleSubmit: React.FormEventHandler<HTMLFormElement> = (event) => {
        event.preventDefault();
        onSubmit(data)
    }

    return (
        <form onSubmit={handleSubmit}>
            {nodes}
            <button>{submitLabel}</button>
        </form>
    )

}

export default Form
