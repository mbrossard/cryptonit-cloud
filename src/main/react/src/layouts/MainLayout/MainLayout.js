import React from 'react'
import PropTypes from 'prop-types'
import Header from '../../components/Header'
import './MainLayout.scss'

export const MainLayout = ({ children }) => (
  <div className='container text-center'>
    <Header />
    <div className='main-layout-content'>
      {children}
    </div>
  </div>
)

MainLayout.propTypes = {
  children : PropTypes.element.isRequired
}

export default MainLayout
