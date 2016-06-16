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
						<PersonDetails person={this.getPerson(this.state.selectedPersonName)}/> : 'Choose a person'}
				</div>
			</div>
		);
	}
});

var PersonDetails = React.createClass({
	componentDidMount: function () {
		//TODO detta kan inte göras vid mount, det får göras när props ändras typ
		console.log("componentDidMount");
		$.ajax({
			url: "http://localhost:8000/backend/log",
			data: { name: this.props.person.name},
			dataType: 'json',
			cache: false,
			success: function (data) {
				console.log("success");
				this.setState({data: data});
			}.bind(this),
			error: function (xhr, status, err) {
				console.log("fail");
				console.log(this.props.startUrl + "," + status + "," + err.toString());
			}.bind(this)
		});
	},
	render: function () {
		//TODO ta å måla ut infon som vi just nu får i state...
		return (
			<div>
				<h1>{this.props.person.name}</h1>

				<div>Money: {this.props.person.money}</div>

				{this.props.person.ship != undefined ?
					<ShipInfo ship={this.props.person.ship}/> : null
				}

			</div>
		);
	}
});

export default PersonDialog;