import React from 'react';
import ReactDOM from 'react-dom';
import { createStore, applyMiddleware } from 'redux';
import { Provider } from 'react-redux';
import thunk from 'redux-thunk';
import promiseMiddleware from 'redux-promise-middleware';
import { AppContainer } from 'react-hot-loader';

import { errorHandlerMiddleware } from './errorHandlerMiddleware';

// Load global css rules before our Main component, so we can overwrite them in our components
// import '../css/global.scss';
// import '../css/_util_global.scss';
// import '../css/styles.scss';

import Main from './main';
import rootReducer from './rootReducer';


const store = createStore(rootReducer,
  window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__(), // eslint-disable-line no-underscore-dangle
  applyMiddleware(thunk, promiseMiddleware(), errorHandlerMiddleware));

const content = document.getElementById('content');

ReactDOM.render(
  <AppContainer>
    <Provider store={store}>
      <Main />
    </Provider>
  </AppContainer>,
  content,
);

// Hotswap in changes:
if (module.hot) {
  module.hot.accept('./main', () => {
    const UpdatedApp = require('./main').default; // eslint-disable-line global-require
    ReactDOM.render(
      <AppContainer>
        <Provider store={store}>
          <UpdatedApp />
        </Provider>
      </AppContainer>,
      content,
    );
  });
}
