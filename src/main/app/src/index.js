import React from 'react';
import ReactDOM from 'react-dom';
import createStore from './store/createStore'
import AppContainer from './containers/AppContainer'
import registerServiceWorker from './registerServiceWorker';
import './index.css';

const initialState = window.__INITIAL_STATE__
const store = createStore(initialState)
const routes = require('./routes/index').default(store)

ReactDOM.render(<AppContainer store={store} routes={routes} />,
  document.getElementById('root'));
registerServiceWorker();
