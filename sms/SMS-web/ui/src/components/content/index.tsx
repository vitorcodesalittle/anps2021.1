import React from 'react'
import Navbar from '../navbar'

const Content: React.FC = function (props) {
  return <div>
    <Navbar/>
    {props.children}
  </div>
}
    
export default Content