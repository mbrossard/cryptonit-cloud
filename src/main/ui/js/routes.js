import React, { Component } from 'react';
import { Router, browserHistory } from 'react-router';

export default class Routes extends Component {
  render() {
    return (
      <Router history={ browserHistory }>
      </Router>
    );
  }
}
