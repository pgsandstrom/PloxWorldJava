import { combineReducers } from 'redux';

import globalReducer from './global/reducer';
import worldReducer from './reducer';

const rootReducer = combineReducers({
  globalReducer,
  worldReducer,
});

export default rootReducer;
