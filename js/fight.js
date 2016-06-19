import React from 'react';
import $ from 'jquery';

const Fight = React.createClass({
	componentWillMount: function () {
		 console.log("componentWillMount Fight ");
		this.makeAction();
	},
	componentWillReceiveProps: function (nextProps) {
		 console.log("componentWillReceiveProps Fight");
		this.makeAction();
	},
	makeAction: function () {
		$.ajax({
			url: "http://localhost:8000/backend/action",
			data: {decision: "FIRE"},
			dataType: 'json',
			cache: false,
			success: function (logs) {
				console.log("fight ajax data: " + JSON.stringify(data));
				this.setState({logs: logs});
			}.bind(this),
			error: function (xhr, status, err) {
				console.log("fail");
				console.log(this.props.startUrl + "," + status + "," + err.toString());
			}.bind(this)
		});
	},
	render: function () {
		console.log("Fight render: " + JSON.stringify(this.props));
		//console.log("Fight render: " + JSON.stringify(this.props));
		//console.log("Fight render: " + JSON.stringify(this.props));
		//console.log("Fight render: " + JSON.stringify(this.props));
		//console.log("Fight render: " + JSON.stringify(this.props));
		//console.log("Fight render: " + JSON.stringify(this.props));
		//console.log("Fight render: " + JSON.stringify(this.props));
		//console.log("Fight render: " + JSON.stringify(this.props));
		//console.log("Fight render: " + JSON.stringify(this.props));
		//console.log("Fight render: " + JSON.stringify(this.props));
		//console.log("Fight render: " + JSON.stringify(this.props));
		//console.log("Fight render: " + JSON.stringify(this.props));
		//console.log("Fight render: " + JSON.stringify(this.props));
		//console.log("Fight render: " + JSON.stringify(this.props));

		//var storage = [];
		//for (var item in this.props.ship.storage) {
		//	storage.push({type: item, amount: this.props.ship.storage[item]});
		//}

		return (
			<div>
				<h2>OMG LE FIGHT</h2>
			</div>
		);
	}
});

export default Fight;