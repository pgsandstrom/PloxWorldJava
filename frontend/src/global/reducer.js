import { browserHistory } from 'react-router';

import { ERROR_RAISED, ERROR_DISMISSED, ERROR_DISMISSED_AND_NAVIGATED } from './constants';

const initialState = {
  error: null,
};

const navigateTo = (path) => {
  browserHistory.push(path);
};

export default (state = initialState, action) => {
  switch (action.type) {
    case ERROR_RAISED:
      return { ...state, error: action.payload };
    case ERROR_DISMISSED:
      return { ...state, error: null };
    case ERROR_DISMISSED_AND_NAVIGATED:
      navigateTo(action.payload.url);
      return { ...state, error: null };
    default:
      return { ...state };
  }
};
