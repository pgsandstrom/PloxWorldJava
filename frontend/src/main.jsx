import React from 'react';
import { connect } from 'react-redux';

import { newGame, progressTurn, setShowLocationList, setShowPersonList } from './actions';
// import MessageSystem from './messageSystem.js';
import Board from './board';
// import Dialog from './dialog.js';
// import Fight from './fight/fight.js';
import GameInfo from './gameInfo';
import LocationDialog from './locationDialog';
import PersonDialog from './personDialog';
import WorldStats from './worldStats';


class Main extends React.Component {
  render() {
    if (this.props.world == null) {
      return (<div className="main">
        <span>wanna start a game?</span>
        <button onClick={this.props.newGame}>New game</button>
      </div>);
    }


    const disableProgress = false;

    // TODO make a better check if Board and gameinfo is to be rendered lol.... also board should keep on living, wtf, its overridden...
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

        {this.props.world.width &&
        <Board /> }

        {this.props.world.width &&
        <GameInfo world={this.props.world} />}

        {this.props.world.worldData != null &&
        <WorldStats className="WorldStats" worldData={this.props.world.worldData} />}

        {this.props.showLocationList &&
        <LocationDialog />}

        {this.props.showPersonList &&
        <PersonDialog />}

        {/* { this.state.action === 'ProposalAction' ? <Dialog data={this.state.info} /> : null }*/}
        {/* { this.state.action === 'FightAction' ? <Fight data={this.state.info} /> : null }*/}
      </div>
    );
  }
}
Main.propTypes = {
  world: React.PropTypes.object,
  showLocationList: React.PropTypes.bool.isRequired,
  showPersonList: React.PropTypes.bool.isRequired,
  newGame: React.PropTypes.func.isRequired,
  progressTurn: React.PropTypes.func.isRequired,
  setShowLocationList: React.PropTypes.func.isRequired, // eslint-disable-line react/no-unused-prop-types
  setShowPersonList: React.PropTypes.func.isRequired, // eslint-disable-line react/no-unused-prop-types
};

export default connect(state => ({
  world: state.worldReducer.world,
  showLocationList: state.worldReducer.showLocationList,
  showPersonList: state.worldReducer.showPersonList,
}), {
  newGame,
  progressTurn,
  setShowLocationList,
  setShowPersonList,
})(Main);
