import React, { useState } from 'react'
import Form from '../../components/form'
import { fail, Schema, success } from '../../pkg/form'
import { getProducts, login, signUp } from '../../pkg/smsApi/api'

type SignUpDataWithConfirmPassword = SignUpData & {
    confirmPassword: string;
}

const defaultLoginData = (): LoginData => ({
  email: '',
  password: ''
})

const defaultSignUpData = (): SignUpDataWithConfirmPassword => ({
  email: '',
  name: '',
  password: '',
  storeName: '',
  confirmPassword: ''
})
const LoginSchema: Schema<LoginData> = {
    email: {
        type: "text",
        htmlType: "text",
        label: "Email",
        validate: (data, value) => {
            if (value === "") return fail("Email cannot be empty")
            return success()
        },
        onChange: (data, value) => ({...data, email: value}),
        order: 0
    },
    password: {
        htmlType: "password",
        type: "text",
        label: "Password",
        validate: (data, value) => {
            if (value === "") return fail("Password cannot be empty")
            return success()
        },
        onChange: (data, value) => ({...data, password: value}),
        order: 1
    }
}

const SignUpSchema: Schema<SignUpDataWithConfirmPassword> = {
    email: {
        type: "text",
        htmlType: "text",
        label: "Email",
        validate: (data, value) => {
            if (value === "") return fail("Email cannot be empty")
            return success()
        },
        onChange: (data, value) => ({...data, email: value}),
        order: 0
    },
    password: {
        htmlType: "password",
        type: "text",
        label: "Password",
        validate: (data, value) => {
            if (value === "") return fail("Password cannot be empty")
            return success()
        },
        onChange: (data, value) => ({...data, password: value}),
        order: 1
    },
    name: {
        type: "text",
        htmlType: "text",
        label: "Name",
        validate: (data, value) => {
            if (value === "") return fail("Email cannot be empty")
            return success()
        },
        onChange: (data, value) => ({...data, name: value}),
        order: 0
    },
    storeName: {
        type: "text",
        htmlType: "text",
        label: "Store Name",
        validate: (data, value) => {
            if (value === "") return fail("Email cannot be empty")
            return success()
        },
        onChange: (data, value) => ({...data, storeName: value}),
        order: 0
    },
    confirmPassword:{
        htmlType: "password",
        type: "text",
        label: "Confirm Password",
        validate: (data, value) => {
            if (value !== data.password) return fail("'Password' and 'Confirm Password' are not equals")
            return success()
        },
        onChange: (data, value) => ({...data, confirmPassword: value}),
        order: 1
    }
}

function Auth() {
  const handleLogin = (loginData: LoginData) => {
    login(loginData)
        .then(console.log)
        .catch(console.error)
  }
  const handleCreateAccount = (signUpData: SignUpDataWithConfirmPassword) => {
      signUp(signUpData)
        .then(console.log)
        .catch(console.error)
  }
  return <div>
    <div>
      <h2>Login</h2>
      <Form<LoginData> schema={LoginSchema} initialState={defaultLoginData()} onSubmit={handleLogin} submitLabel="Login"/>
      <button onClick={() => { getProducts().then(console.log).catch(console.error)}}>Print products page ????</button>
    </div>
    <div>
      <h2>Register</h2>
        <Form<SignUpDataWithConfirmPassword> schema={SignUpSchema} onSubmit={handleCreateAccount} submitLabel="Create Account" initialState={defaultSignUpData()}/>
    </div>
  </div>
}

export default Auth