import React from 'react'
import ReactDOM from 'react-dom'
import createStore from './store/createStore'
import AppContainer from './containers/AppContainer'

const store = createStore(initialState);

let render = () => {
    const routes = require('./routes/index').default(store);

    ReactDOM.render(
        <AppContainer store={store} routes={routes} />,
        document.getElementById('root')
    );
}

render();
