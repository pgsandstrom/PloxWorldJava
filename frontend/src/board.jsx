import React from 'react';
import { connect } from 'react-redux';

import { setSelectedLocation, setSelectedPerson } from './actions';

import './board.scss';

const Board = (props) => {
  const style = {
    'height': `${props.world.height}px`,
    'width': `${props.world.width}px`,
  };
  return (
    <div className="board" style={style}>
      {props.world.locations.map(location =>
        <Location key={location.name} location={location} setSelectedLocation={props.setSelectedLocation} />,
        )}
      {props.world.persons.map(person =>
        <Person key={person.name} person={person} setSelectedPerson={props.setSelectedPerson} />,
        )}
    </div>
  );
};
Board.propTypes = {
  world: React.PropTypes.object.isRequired,
  setSelectedLocation: React.PropTypes.func.isRequired, // eslint-disable-line react/no-unused-prop-types
  setSelectedPerson: React.PropTypes.func.isRequired,
};

const Location = (props) => {
  const style = {
    'left': `${props.location.point.x - 15}px`,
    'top': `${props.location.point.y - 15}px`,
  };

  let image;
  if (props.location.locationStyle === 'PLANET') {
    image = 'img/planet.png';
  } else if (props.location.locationStyle === 'ASTEROID') {
    image = 'img/asteroid.png';
  } else {
    image = 'img/BUGG.png';
  }

  return (
    <button className="location" style={style} onClick={() => props.setSelectedLocation(props.location)}>
      <img src={image} alt="location" />
      <span>
        {props.location.name}
      </span>
    </button>
  );
};
Location.propTypes = {
  location: React.PropTypes.object.isRequired,
  setSelectedLocation: React.PropTypes.func.isRequired, // eslint-disable-line react/no-unused-prop-types
};

const Person = (props) => {
  const style = {
    'left': `${props.person.point.x - 5}px`,
    'top': `${props.person.point.y - 6}px`,
  };
  return (
    <button className="person" style={style} onClick={() => props.setSelectedPerson(props.person)}>
      <img src="img/ship_ai.png" alt="ship" />
    </button>
  );
};
Person.propTypes = {
  person: React.PropTypes.object.isRequired,
  setSelectedPerson: React.PropTypes.func.isRequired, // eslint-disable-line react/no-unused-prop-types
};

export default connect(status => ({
  world: status.worldReducer.world,
}), {
  setSelectedLocation,
  setSelectedPerson,
})(Board);
