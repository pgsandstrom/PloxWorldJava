import React from 'react';

const GameInfo = (props) => {
  // this.props.world.persons.sort((a, b) => b.money - a.money);
  return (
    <div className="gameInfo">
      <h2>Top list</h2>
      {props.world.persons.map(person =>
        <div key={person.name}>
          {person.ai} : {person.name} : {person.money}
        </div>,
      )}
    </div>);
};
GameInfo.propTypes = {
  world: React.PropTypes.object.isRequired, //eslint-disable-line react/no-unused-prop-types
};

export default GameInfo;
