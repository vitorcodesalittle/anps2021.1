import React, { useState } from 'react'


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

function Auth() {
  const [loginData, setLoginData] = useState<LoginData>(defaultLoginData())
  const [signUpData, setSignUpData] = useState<SignUpDataWithConfirmPassword>(defaultSignUpData())
  const handleLogin: React.FormEventHandler<HTMLFormElement> = (event) => {
    event.preventDefault()
    console.log(loginData)
  }
  const handleCreateAccount: React.FormEventHandler<HTMLFormElement> = (event) => {
    event.preventDefault()
    console.log(signUpData)
  }
  const changeLoginData = (partial: Partial<LoginData>) => setLoginData({...loginData, ...partial})
  const changeSignUpData  = (partial: Partial<SignUpDataWithConfirmPassword>) => setSignUpData({...signUpData, ...partial})
  return <div>
    <div>
      <h2>Login</h2>
      <form onSubmit={handleLogin}>
        <label>Email</label>
        <input type="email" name="email" onChange={event => changeLoginData({email: event.target.value})}/>
        <label>Senha</label>
        <input type="password" name="password" onChange={event => changeLoginData({password: event.target.value})}/>
        <button type="submit">Login</button>
      </form>
    </div>
    <div>
      <h2>Register</h2>
      <form onSubmit={handleCreateAccount}>
        <label>Email</label>
        <input type="email" name="email"  onChange={event => changeSignUpData({email: event.target.value})}/>
        <label>Nome</label>
        <input type="text" name="name"  onChange={event => changeSignUpData({name: event.target.value})}/>
        <label>Senha</label>
        <input type="password" name="password" onChange={event => changeSignUpData({password: event.target.value})}/>
        <label>Confirmar Senha</label>
        <input type="password" name="confirmPassword"  onChange={event => changeSignUpData({confirmPassword: event.target.value})}/>
        <label>Nome da loja</label>
        <input type="text" name="storeName"  onChange={event => changeSignUpData({storeName: event.target.value})}/>
        <button type="submit">Sign up</button>
      </form>
    </div>
  </div>
}

export default Auth