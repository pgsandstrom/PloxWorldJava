import React from 'react';
import { connect } from 'react-redux';
import ReactCSSTransitionGroup from 'react-addons-css-transition-group';

import { makeDecision } from '../actions';

import './fight.scss';

class Fight extends React.Component {

  componentWillMount() {
    this.startStuff();
  }

  componentWillReceiveProps(nextProps) {
    this.startStuff(nextProps);
  }

  startStuff(nextProps) {
    const state = this.state || {};
    const props = nextProps || this.props;
    if (props.info.acter.unseenTransitions.length > 0) {
      state.transitions = props.info.acter.unseenTransitions;
      // between every transition we need a reset. Because ugly code.
      for (let i = 1; i < state.transitions.length; i += 2) {
        state.transitions.splice(i, 0, { 'actionType': 'RESET' });
      }
    }
    this.setState(state);
  }

  render() {
    // console.log("Fight render: " + JSON.stringify(this.props));
    const self = this;
    const distance = this.props.info.fight.distance;

    const backgroundStyle = {};
    let playingTransitions = false;
    if (this.state && this.state.transitions && this.state.transitions.length > 0) {
      playingTransitions = true;
      const transition = this.state.transitions[0];
      console.log(`transition: ${JSON.stringify(transition)}`);
      let timeoutTime;
      if (transition.actionType === 'MOVE_FORWARD') {
        backgroundStyle.animation = 'animatedBackgroundLeft 0.5s ease-in-out normal';
        timeoutTime = 1000;
      } else if (transition.actionType === 'MOVE_BACKWARD') {
        backgroundStyle.animation = 'animatedBackgroundRight 0.5s ease-in-out normal';
        timeoutTime = 1000;
      } else if (transition.actionType === 'FIRE') {
        // TODO
        timeoutTime = 100;
      } else if (transition.actionType === 'WAIT') {
        // TODO
        timeoutTime = 100;
      } else if (transition.actionType === 'ESCAPE') {
        // TODO
        timeoutTime = 100;
      } else if (transition.actionType === 'RESET') {
        backgroundStyle.animation = '';
        timeoutTime = 1;
      }
      setTimeout(() => {
        self.state.transitions.splice(0, 1);
        self.setState(self.state);
      }, timeoutTime);
    }

    const playerStyle = {
      'left': `${575 - (distance * 50)}px`,
    };
    const opponentStyle = {
      'right': `${575 - (distance * 50)}px`,
    };

    // TODO do I really need the ReactCSSTransitionGroup? If not, remove from package.json
    return (
      <div className="dialog">
        <h2>OMG LE FIGHT</h2>
        <button disabled={playingTransitions} onClick={() => this.props.makeDecision('FIRE')}>
          FIRE
        </button>
        <button disabled={playingTransitions || distance === 1} onClick={() => this.props.makeDecision('MOVE_FORWARD')}>
          MOVE_FORWARD
        </button>
        <button disabled={playingTransitions} onClick={() => this.props.makeDecision('MOVE_BACKWARD')}>
          MOVE_BACKWARD
        </button>
        <button disabled={playingTransitions} onClick={() => this.props.makeDecision('ESCAPE')}>
          ESCAPE
        </button>
        <button disabled={playingTransitions} onClick={() => this.props.makeDecision('WAIT')}>
          WAIT
        </button>
        <ReactCSSTransitionGroup>
          <div className="fight-board" style={backgroundStyle}>
            <img className="ship player" style={playerStyle} src="img/ship_ai.png" alt="" />
            <img className="ship opponent" style={opponentStyle} src="img/ship_ai.png" alt="" />
          </div>
        </ReactCSSTransitionGroup>
      </div>
    );
  }
}
Fight.propTypes = {
  info: React.PropTypes.object.isRequired,
  makeDecision: React.PropTypes.func.isRequired,
};

export default connect(null, {
  makeDecision,
})(Fight);
