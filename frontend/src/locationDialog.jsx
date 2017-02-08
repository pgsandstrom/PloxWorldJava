import React from 'react';
import { connect } from 'react-redux';

import { setShowLocationList, setSelectedLocation } from './actions';
import CivilizationInfo from './civilizationInfo';
import TradeableInfo from './tradeableInfo';

import './dialog.scss';

const LocationDialog = props => (
  <div className="dialog">
    <div style={{ float: 'left' }}>
      <button onClick={() => props.setShowLocationList(false)}>Close</button>

      {props.locations.map(location =>
        <div key={location.name} >
          <button onClick={() => props.setSelectedLocation(location)}>
            {location.name}
          </button>
        </div>)}
    </div>

    <div style={{ float: 'left' }}>
      {props.selectedLocation != null ?
        <LocationDetails location={props.selectedLocation} /> : 'Choose a location'}
    </div>
  </div>
  );
LocationDialog.propTypes = {
  locations: React.PropTypes.array.isRequired,
  selectedLocation: React.PropTypes.object,
  setShowLocationList: React.PropTypes.func.isRequired, // eslint-disable-line react/no-unused-prop-types
  setSelectedLocation: React.PropTypes.func.isRequired, // eslint-disable-line react/no-unused-prop-types
};

const LocationDetails = props => (
  <div>
    <h1>{props.location.name}</h1>
    {props.location.civilization != null ?
      <CivilizationInfo civilization={props.location.civilization} /> : null
    }
    {props.location.tradeable != null ?
      <TradeableInfo tradeable={props.location.tradeable} /> : null
    }
  </div>
);
LocationDetails.propTypes = {
  location: React.PropTypes.object.isRequired,
};

export default connect(state => ({
  locations: state.worldReducer.world.locations,
  selectedLocation: state.worldReducer.selectedLocation,
}), {
  setShowLocationList,
  setSelectedLocation,
})(LocationDialog);
