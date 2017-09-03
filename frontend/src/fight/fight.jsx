import React from 'react';
import { connect } from 'react-redux';
import TransitionGroup from 'react-addons-transition-group';

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
    if (props.info.acter.person.unseenTransitions.length > 0) {
      state.transitions = [...props.info.acter.person.unseenTransitions];
      // between every transition we need a reset. Because ugly code.
      for (let i = 1; i < state.transitions.length; i += 2) {
        state.transitions.splice(i, 0, { 'actionType': 'RESET' });
      }
      state.transitions.splice(0, 0, { 'actionType': 'RESET' });
    }
    this.setState(state);
  }

  render() {
    const self = this;
    let distance = this.props.info.fight.distance; // TODO make sure there is an initial transition instead?

    const backgroundStyle = {};
    const shotContainerStyle = {};
    const shotStyle = {};
    let isShooting = false;
    let playingTransitions = false;
    if (this.state && this.state.transitions && this.state.transitions.length > 0) {
      playingTransitions = true;
      const transition = this.state.transitions[0];
      const isPlayerTransition = this.props.info.acter.person.name === transition.actorName;
      distance = transition.distance;
      let timeoutTime;
      if (transition.name === 'move') {
        const isClosing = transition.startDistance < transition.distance;
        if (isClosing !== isPlayerTransition) {
          backgroundStyle.animation = 'animatedBackgroundLeft 0.5s linear normal';
          timeoutTime = 1000;
        } else {
          backgroundStyle.animation = 'animatedBackgroundRight 0.5s linear normal';
          timeoutTime = 1000;
        }
      } else if (transition.name === 'shoot') {
        isShooting = true;
        shotContainerStyle.left = `${575 - (distance * 100)}px`;
        shotContainerStyle.width = `${(distance * 200) - 50}px`;
        if (isPlayerTransition) {
          shotStyle['animation-name'] = 'shot-player';
        }
        timeoutTime = 1000;
      } else if (transition.name === 'wait') {
        // TODO
        timeoutTime = 100;
      } else if (transition.name === 'escape') {
        // TODO
        timeoutTime = 100;
      } else if (transition.name === 'reset') {
        backgroundStyle.animation = '';
        timeoutTime = 1;
      }
      setTimeout(() => {
        self.state.transitions.splice(0, 1);
        self.setState(self.state);
      }, timeoutTime);
    }

    const playerStyle = {
      'left': `${575 - (distance * 100)}px`,
    };
    const opponentStyle = {
      'right': `${575 - (distance * 100)}px`,
    };

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
        <TransitionGroup>
          <div className="fight-board" style={backgroundStyle}>
            <img className="ship player" style={playerStyle} src="img/ship_ai.png" alt="" />
            <img className="ship opponent" style={opponentStyle} src="img/ship_ai.png" alt="" />
            {
              isShooting &&
              <div id="shot-container" style={shotContainerStyle}>
                <div id="shot" style={shotStyle} />
              </div>
            }
          </div>
        </TransitionGroup>
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
