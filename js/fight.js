import React from 'react';
import $ from 'jquery';

import MessageSystem from './messageSystem.js';

const Fight = React.createClass({
	componentWillMount: function () {
		console.log("componentWillMount Fight");
		this.makeAction();
	},
	componentWillReceiveProps: function (nextProps) {
		console.log("componentWillReceiveProps Fight");
		this.makeAction();
	},
	makeAction: function () {
		MessageSystem.dispatch(MessageSystem.makeAjax,
			{url: "http://localhost:8000/backend/action", data: {decision: "FIRE"}});
	},
	render: function () {
		console.log("Fight render: " + JSON.stringify(this.props));

		return (
			<div>
				<h2>OMG LE FIGHT</h2>
			</div>
		);
	}
});

export default Fight;