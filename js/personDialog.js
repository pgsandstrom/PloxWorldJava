import React from 'react';
import $ from 'jquery';

import ShipInfo from './shipInfo.js';

const PersonDialog = React.createClass({
	selectPerson: function (selectedPersonName) {
		this.setState({selectedPersonName: selectedPersonName});
	},
	getPerson: function (personName) {
		return this.props.persons.find(function (element) {
			if (element.name == personName) {
				return element;
			} else {
				return false;
			}
		});
	},
	componentDidMount: function () {
		var selectedPersonName = this.props.selectedPersonName;
		if (selectedPersonName !== undefined) {
			this.selectPerson(selectedPersonName);
		}
	},
	render: function () {
		var self = this;
		return (
			<div className="dialog">
				<div style={{float:'left'}}>
					<button onClick={this.props.requestClose}>
						close
					</button>

					{this.props.persons.map(function (person) {
						return (
							<div key={person.name} onClick={self.selectPerson.bind(self, person.name)}>
								{person.name}
							</div>
						);
					})}
				</div>

				<div style={{float:'left'}}>
					{this.state != undefined && this.state.selectedPersonName != undefined ?
						<PersonDetailsContainer person={this.getPerson(this.state.selectedPersonName)}/> : 'Choose a person'}
				</div>
			</div>
		);
	}
});

var PersonDetailsContainer = React.createClass({
	componentWillMount: function () {
		// console.log("componentWillMount PersonDetailsContainer ");
		this.getLogs();
	},
	componentWillReceiveProps: function (nextProps) {
		// console.log("componentWillReceiveProps");
		this.getLogs();
	},
	getLogs: function () {
		$.ajax({
			url: "http://localhost:8000/backend/log",
			data: {name: this.props.person.name},
			dataType: 'json',
			cache: false,
			success: function (logs) {
				console.log("success: " + logs);
				this.setState({logs: logs});
			}.bind(this),
			error: function (xhr, status, err) {
				console.log("fail");
				console.log(this.props.startUrl + "," + status + "," + err.toString());
			}.bind(this)
		});
	},
	render: function () {
		var logs = this.state != undefined ? this.state.logs : undefined;
		console.log("logs: " + logs);
		console.log("state: " + JSON.stringify(this.state));
		return (
			<PersonDetails person={this.props.person} logs={logs}/>
		);
	}
});

var PersonDetails = React.createClass({
	componentDidMount: function () {
		console.log("componentDidMount PersonDetails ")
	},
	render: function () {
		console.log("Person render: " + JSON.stringify(this.props));
		var logs = this.props.logs != undefined ? this.props.logs : [];
		return (
			<div>
				<h1>{this.props.person.name}</h1>

				<div>Money: {this.props.person.money}</div>

				{this.props.person.ship != undefined ?
					<ShipInfo ship={this.props.person.ship}/> : null
				}

				<div>
					<div className="cell header">The log</div>
					{logs.map(function (log) {
						return (
							<div className="cellRow">
								{log}
							</div>
						);
					})}
				</div>

			</div>
		);
	}
});

export default PersonDialog;