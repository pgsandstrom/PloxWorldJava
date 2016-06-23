import React from 'react';
import $ from 'jquery';

require("./../css/fight.css");

import MessageSystem from './messageSystem.js';

const Fight = React.createClass({
	componentWillMount: function () {
		console.log("componentWillMount Fight");
		//this.makeAction();
	},
	componentWillReceiveProps: function (nextProps) {
		console.log("componentWillReceiveProps Fight");
		//this.makeAction();
	},
	makeAction: function (decision) {
		MessageSystem.dispatch(MessageSystem.makeAjax,
			{url: "http://localhost:8000/backend/action", data: {decision: decision}});
	},
	render: function () {
		console.log("Fight render: " + JSON.stringify(this.props));
		var self = this;

		return (
			<div className="dialog">
				<h2>OMG LE FIGHT</h2>
				<button onClick={self.makeAction.bind(self, "FIRE")}>
					FIRE
				</button>
				<button onClick={self.makeAction.bind(self, "MOVE_FORWARD")}>
					 MOVE_FORWARD
				</button>
				<button onClick={self.makeAction.bind(self, "MOVE_BACKWARD")}>
					MOVE_BACKWARD
				</button>
				<button onClick={self.makeAction.bind(self, "ESCAPE")}>
					ESCAPE
				</button>
				<button onClick={self.makeAction.bind(self, "WAIT")}>
					WAIT
				</button>
			</div>
		);
	}
});

export default Fight;