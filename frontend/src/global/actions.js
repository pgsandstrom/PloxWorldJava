import { getServerUrl } from '../backend';
import { ERROR_RAISED, ERROR_DISMISSED, ERROR_DISMISSED_AND_NAVIGATED } from './constants';

export const raiseError = (title, body, technicalError, errorCode) => ({
  type: ERROR_RAISED,
  payload: {
    title,
    body,
    technicalError,
    errorCode,
  },
});

export const dismissError = () => ({
  type: ERROR_DISMISSED,
});

export const dismissErrorAndNavigate = url => ({
  type: ERROR_DISMISSED_AND_NAVIGATED,
  payload: {
    url,
  },
});

const urlifyParams = (params) => {
  if (params == null || typeof params !== 'object' || Object.keys(params).length === 0) {
    return '';
  } else {
    const paramString = Object.keys(params).filter(key => params[key] != null).map(key => `${key}=${params[key]}`).join('&');
    return `?${paramString}`;
  }
};

export const doFetch = (name, path, params, showError = true, options = { credentials: 'same-origin' }) => dispatch =>
  fetch(`${getServerUrl()}${path}${urlifyParams(params)}`,
    options,
  )
    .then((response) => {
      if (response.ok) {
        return response.json();
      } else {
        return Promise.reject(`${name} svar kunde inte hanteras\nStatus code: ${response.status}\nStatus text: ${response.statusText}`);
      }
    })
    .then((data) => {
      if (!data.error) {
        return data;
      } else {
        return Promise.reject(data);
      }
    })
    .catch((error) => {
      if (showError) {
        dispatch(buildErrorMessage(name, error.error || error, error.code));
      }
      return Promise.reject(error);
    });

const buildErrorMessage = (name, reason, errorCode) => raiseError('Ett fel har inträffat', `${name} kunde inte hämtas`, reason, errorCode);

