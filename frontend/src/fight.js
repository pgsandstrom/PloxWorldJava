import React from 'react';
import ReactCSSTransitionGroup from 'react-addons-css-transition-group';
import $ from 'jquery';

require("./../css/fight.css");

import MessageSystem from './messageSystem.js';

const Fight = React.createClass({
	componentWillMount: function () {
		console.log("componentWillMount Fight");
		this.startStuff();
	},
	componentWillReceiveProps: function (nextProps) {
		console.log("componentWillReceiveProps Fight");
		this.startStuff(nextProps);
	},
	startStuff: function (nextProps) {
		var state = this.state || {};
		var props = nextProps || this.props;
		if (props.data.acter.unseenTransitions.length > 0) {
			state.transitions = props.data.acter.unseenTransitions;
			// between every transition we need a reset. Because ugly code.
			for (var i = 1; i < state.transitions.length; i = i + 2) {
				state.transitions.splice(i, 0, {"actionType": "RESET"});
			}
		}
		this.setState(state);
	},
	makeAction: function (decision) {
		MessageSystem.dispatch(MessageSystem.makeAjax,
			{url: "http://localhost:8000/backend/action", data: {decision: decision}});
	},
	render: function () {
		//console.log("Fight render: " + JSON.stringify(this.props));
		var self = this;
		var distance = this.props.data.fight.distance;

		var background_style = {};
		var playingTransitions = false;
		if (this.state && this.state.transitions && this.state.transitions.length > 0) {
			playingTransitions = true;
			var transition = this.state.transitions[0];
			console.log("transition: " + JSON.stringify(transition));
			var timeoutTime;
			if (transition.actionType === "MOVE_FORWARD") {
				background_style.animation = 'animatedBackgroundLeft 0.5s ease-in-out normal';
				timeoutTime = 1000;
			} else if (transition.actionType === "MOVE_BACKWARD") {
				background_style.animation = 'animatedBackgroundRight 0.5s ease-in-out normal';
				timeoutTime = 1000;
			} else if (transition.actionType === "FIRE") {
				//TODO
				timeoutTime = 100;
			} else if (transition.actionType === "WAIT") {
				//TODO
				timeoutTime = 100;
			} else if (transition.actionType === "ESCAPE") {
				//TODO
				timeoutTime = 100;
			} else if (transition.actionType === "RESET") {
				background_style.animation = '';
				timeoutTime = 1;
			}
			setTimeout(function () {
				self.state.transitions.splice(0, 1);
				self.setState(self.state);
			}, timeoutTime);
		}

		var player_style = {
			'left': (575 - distance * 50) + 'px'
		};
		var opponent_style = {
			'right': (575 - distance * 50) + 'px'
		};

		//TODO do I really need the ReactCSSTransitionGroup? If not, remove from package.json
		return (
			<div className="dialog">
				<h2>OMG LE FIGHT</h2>
				<button disabled={playingTransitions} onClick={self.makeAction.bind(self, "FIRE")}>
					FIRE
				</button>
				<button disabled={playingTransitions || distance == 1} onClick={self.makeAction.bind(self, "MOVE_FORWARD")}>
					MOVE_FORWARD
				</button>
				<button disabled={playingTransitions} onClick={self.makeAction.bind(self, "MOVE_BACKWARD")}>
					MOVE_BACKWARD
				</button>
				<button disabled={playingTransitions} onClick={self.makeAction.bind(self, "ESCAPE")}>
					ESCAPE
				</button>
				<button disabled={playingTransitions} onClick={self.makeAction.bind(self, "WAIT")}>
					WAIT
				</button>
				<ReactCSSTransitionGroup>
					<div className="fight-board" style={background_style}>
						<img className="ship player" style={player_style} src="img/ship_ai.png" alt=""/>
						<img className="ship opponent" style={opponent_style} src="img/ship_ai.png" alt=""/>
					</div>
				</ReactCSSTransitionGroup>
			</div>
		);
	}
});

export default Fight;