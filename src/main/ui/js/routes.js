import React, { Component } from 'react';
import { Router, Route, browserHistory } from 'react-router';

import Layout from './layout/layout.js'

export default class Routes extends Component {
  render() {
    return (
      <Router history={ browserHistory }>
        <Route path="/" component={Layout}>
        </Route>
      </Router>
    );
  }
}
