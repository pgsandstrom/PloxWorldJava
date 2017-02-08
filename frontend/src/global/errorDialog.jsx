import React from 'react';
import { connect } from 'react-redux';

import { dismissError, dismissErrorAndNavigate } from './actions';

import './errorDialog.scss';

const ErrorDialogWrapper = (props) => {
  if (props == null || props.error == null) {
    return null;
  }
  const technicalError = getTechnicalError(props.error);

  if (props.error.errorCode === 160105) {
    return <ConnectedErrorDialog title="Oh noes" body="Extra super error!" />;
  }
  return (<ConnectedErrorDialog title={props.error.title} body={props.error.body} technicalError={technicalError} />);
};
ErrorDialogWrapper.propTypes = {
  error: React.PropTypes.object,
};

const getTechnicalError = (error) => {
  if (error.technicalError != null && typeof error.technicalError === 'object') {
    if (error.technicalError.message != null) {
      return error.technicalError.message;
    } else {
      return JSON.stringify(error.technicalError);
    }
  } else {
    return error.technicalError;
  }
};

const ErrorDialog = props => (
  <div className="error-dialog-container">
    <div className="error-dialog">
      <div className="title">{props.title}</div>
      <div className="body">{props.body}</div>
      {props.technicalError && <TechnicalError message={props.technicalError} />}
      {props.useLogoutButton ?
        <button className="primary-button" onClick={() => props.dismissErrorAndNavigate('/login')} autoFocus>Till inloggning</button>
        : <button className="primary-button" onClick={props.dismissError} autoFocus>OK</button>
      }
    </div>
  </div>);
ErrorDialog.propTypes = {
  title: React.PropTypes.string.isRequired,
  body: React.PropTypes.string.isRequired,
  technicalError: React.PropTypes.string,
  useLogoutButton: React.PropTypes.bool,
  dismissError: React.PropTypes.func.isRequired,
  dismissErrorAndNavigate: React.PropTypes.func.isRequired,
};
const ConnectedErrorDialog = connect(null, { dismissError, dismissErrorAndNavigate })(ErrorDialog);

const TechnicalError = props => (
  <div className="technical-error">
    <div>Information till IT-ansvarig:</div>
    <div>{props.message}</div>
  </div>
);
TechnicalError.propTypes = {
  message: React.PropTypes.string,
};


export default ErrorDialogWrapper;
