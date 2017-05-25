import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { Provider } from 'react-redux'
import { BrowserRouter, Route, Switch } from 'react-router-dom'

class AppContainer extends Component {
  static propTypes = {
    routes : PropTypes.object.isRequired,
    store  : PropTypes.object.isRequired
  }

  shouldComponentUpdate () {
    return false
  }

  render () {
    const { routes, store } = this.props

    return (
      <Provider store={store}>
        <div style={{ height: '100%' }}>
          <BrowserRouter>
            <Switch>
              <Route path={routes.path} component={routes.component} childRoutes={routes.childRoutes}/>
            </Switch>
          </BrowserRouter>
        </div>
      </Provider>
    )
  }
}

export default AppContainer
