import { pending, fulfilled } from './errorHandlerMiddleware';
import {
  NEW_GAME,
  PROGRESS_TURN,
  SET_SHOW_LOCATION_LIST,
  SET_SHOW_PERSON_LIST,
  SET_SELECTED_LOCATION,
  SET_SELECTED_PERSON,
  GET_SELECTED_PERSON_LOG,
} from './constants';

const initialState = {
  world: null,
  showLocationList: false,
  showPersonList: false,
  selectedLocation: null,
  selectedPerson: null,
  selectedPersonLog: null,
};


export default (state = initialState, action) => {
  switch (action.type) {
    case NEW_GAME:
      return { ...state, world: action.payload.data };
    case PROGRESS_TURN:
      return { ...state, world: action.payload.data };
    case SET_SHOW_LOCATION_LIST:
      return { ...state, showLocationList: action.payload.bool };
    case SET_SHOW_PERSON_LIST:
      return { ...state, showPersonList: action.payload.bool };
    case SET_SELECTED_LOCATION:
      return { ...state, selectedLocation: action.payload.data, showLocationList: true };
    case SET_SELECTED_PERSON:
      return { ...state, selectedPerson: action.payload.data, showPersonList: true };
    case pending(GET_SELECTED_PERSON_LOG):
      return { ...state, selectedPersonLog: null };
    case fulfilled(GET_SELECTED_PERSON_LOG):
      return { ...state, selectedPersonLog: action.payload };
    default:
      return state;
  }
};
