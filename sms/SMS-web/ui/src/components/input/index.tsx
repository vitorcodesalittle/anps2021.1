import React from 'react'

interface InputProps {
  key?:string;
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

export default Input