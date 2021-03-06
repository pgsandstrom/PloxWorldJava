import React from 'react';
import { connect } from 'react-redux';

import { newGame, progressTurn, setShowLocationList, setShowPersonList } from './actions';
import Board from './board';
import Dialog from './dialog';
import Fight from './fight/fight';
import GameInfo from './gameInfo';
import LocationDialog from './locationDialog';
import PersonDialog from './personDialog';
import WorldStats from './worldStats';

class Main extends React.Component {
  render() {
    if (this.props.world == null && this.props.action == null) {
      return (<div className="main">
        <span>wanna start a game?</span>
        <button onClick={this.props.newGame}>New game</button>
      </div>);
    }


    const disableProgress = false;

    return (
      <div>
        <div>
          <button onClick={this.props.newGame}>New game</button>
          <button onClick={() => this.props.progressTurn(1)} disabled={disableProgress}>Progress Turn lol</button>
          <button onClick={() => this.props.progressTurn(10)} disabled={disableProgress}>Progress 10 Turns</button>
          <button onClick={() => this.props.progressTurn(100)} disabled={disableProgress}>Progress 100 Turns</button>
          <button onClick={() => this.props.setShowLocationList(true)}>Location list</button>
          <button onClick={() => this.props.setShowPersonList(true)}>Person list</button>
        </div>

        {this.props.world &&
        <Board /> }

        {this.props.world &&
        <GameInfo world={this.props.world} />}

        {this.props.world && this.props.world.worldData != null &&
        <WorldStats className="WorldStats" worldData={this.props.world.worldData} />}

        {this.props.showLocationList &&
        <LocationDialog />}

        {this.props.showPersonList &&
        <PersonDialog />}

        { this.props.action && this.props.action.action === 'ProposalAction' && <Dialog info={this.props.action.info} /> }
        { this.props.action && this.props.action.action === 'FightAction' && <Fight info={this.props.action.info} /> }
      </div>
    );
  }
}
Main.propTypes = {
  world: React.PropTypes.object,
  action: React.PropTypes.object,
  showLocationList: React.PropTypes.bool.isRequired,
  showPersonList: React.PropTypes.bool.isRequired,
  newGame: React.PropTypes.func.isRequired,
  progressTurn: React.PropTypes.func.isRequired,
  setShowLocationList: React.PropTypes.func.isRequired, // eslint-disable-line react/no-unused-prop-types
  setShowPersonList: React.PropTypes.func.isRequired, // eslint-disable-line react/no-unused-prop-types
};

export default connect(state => ({
  world: state.worldReducer.world,
  action: state.worldReducer.action,
  showLocationList: state.worldReducer.showLocationList,
  showPersonList: state.worldReducer.showPersonList,
}), {
  newGame,
  progressTurn,
  setShowLocationList,
  setShowPersonList,
})(Main);
