import React from 'react'
import ReactDOM from 'react-dom'
import App from './containers/app'

console.log('Hello from index', ReactDOM, document.getElementById('root'), App)

ReactDOM.render(<App/>, document.getElementById('root'))