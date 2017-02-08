import React from 'react';
import { connect } from 'react-redux';

import { setShowPersonList, setSelectedPerson } from './actions';
import ShipInfo from './shipInfo';

import './dialog.scss';

const PersonDialog = props =>
  <div className="dialog">
    <div style={{ float: 'left' }}>
      <button onClick={() => props.setShowPersonList(false)}>Close</button>

      {props.persons.map(person =>
        <div key={person.name}>
          <button onClick={() => props.setSelectedPerson(person)}>
            {person.name}
          </button>
        </div>)}
    </div>

    <div style={{ float: 'left' }}>
      {props.selectedPerson != null ?
        <PersonDetails person={props.selectedPerson} personLog={props.selectedPersonLog} /> : 'Choose a person'}
    </div>
  </div>;
PersonDialog.propTypes = {
  persons: React.PropTypes.array.isRequired,
  selectedPerson: React.PropTypes.object,
  selectedPersonLog: React.PropTypes.object,
  setShowPersonList: React.PropTypes.func.isRequired, // eslint-disable-line react/no-unused-prop-types
  setSelectedPerson: React.PropTypes.func.isRequired, // eslint-disable-line react/no-unused-prop-types
};

const PersonDetails = props => (
  <div>
    <h1>{props.person.name}</h1>

    <div>Money: {props.person.money}</div>

    {props.person.ship &&
    <ShipInfo ship={props.person.ship} />
      }

    <div>
      <div className="cell header">The log</div>
      {props.personLog == null && <div>Loading...</div>}
      {props.personLog && props.personLog.map((log, index) =>
        <div key={index} className="cellRow">
          {log}
        </div>)}
    </div>

  </div>
  );
PersonDetails.propTypes = {
  person: React.PropTypes.object.isRequired,
  personLog: React.PropTypes.array,
};

export default connect(state => ({
  persons: state.worldReducer.world.persons,
  selectedPerson: state.worldReducer.selectedPerson,
  selectedPersonLog: state.worldReducer.selectedPersonLog,
}), {
  setShowPersonList,
  setSelectedPerson,
})(PersonDialog);

