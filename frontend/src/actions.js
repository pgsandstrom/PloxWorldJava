import {
  NEW_GAME,
  PROGRESS_TURN,
  SET_SHOW_LOCATION_LIST,
  SET_SHOW_PERSON_LIST,
  SET_SELECTED_LOCATION,
  SET_SELECTED_PERSON,
  GET_SELECTED_PERSON_LOG,
} from './constants';
import { doFetch } from './global/actions';

export const newGame = () => (dispatch) => {
  dispatch(doFetch('new game', '/backend'))
    .then(data =>
      dispatch({ type: NEW_GAME, payload: { data },
      }),
    );
};

export const progressTurn = turns => (dispatch) => {
  dispatch(doFetch('progress turn', '/backend/progressTurn', { turns }))
    .then(data =>
      dispatch({ type: PROGRESS_TURN, payload: { data },
      }),
    );
};

export const setShowLocationList = bool => ({
  type: SET_SHOW_LOCATION_LIST,
  payload: {
    bool,
  },
});

export const setShowPersonList = bool => ({
  type: SET_SHOW_PERSON_LIST,
  payload: {
    bool,
  },
});

export const setSelectedLocation = data => ({
  type: SET_SELECTED_LOCATION,
  payload: {
    data,
  },
});

export const setSelectedPerson = data => (dispatch) => {
  dispatch({
    type: SET_SELECTED_PERSON,
    payload: {
      data,
    },
  });
  return dispatch(getSelectedPersonLog());
};

export const getSelectedPersonLog = () => (dispatch, getState) => {
  const selectedPerson = getState().worldReducer.selectedPerson;
  return dispatch({
    type: GET_SELECTED_PERSON_LOG,
    payload: dispatch(doFetch('get person log', '/backend/log', { name: selectedPerson.name })),
  });
};
