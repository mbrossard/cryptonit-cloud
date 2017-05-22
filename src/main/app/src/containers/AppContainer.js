import React from 'react'
import PropTypes from 'prop-types'
import { Router } from 'react-router'

class AppContainer extends React.Component {
  static propTypes = {
    history: PropTypes.object.isRequired,
    routes: PropTypes.object.isRequired,
    routerKey: PropTypes.number,
  }

  render () {
    const { history, routes, routerKey } = this.props

    return (
      <div style={{ height: '100%' }}>
        <Router history={history} children={routes} key={routerKey} />
      </div>
    )
  }
}

export default AppContainer
