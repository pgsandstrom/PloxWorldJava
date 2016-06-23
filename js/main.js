import React from 'react';
import ReactDOM from 'react-dom';
import $ from 'jquery';

import MessageSystem from './messageSystem.js';
import Board from './board.js';
import Dialog from './dialog.js';
import Fight from './fight.js';
import GameInfo from './gameInfo.js';
import LocationDialog from './locationDialog.js';
import PersonDialog from './personDialog.js';
import WorldStats from './worldStats.js';


var Main = React.createClass({
	componentDidMount: function () {
		var self = this;
		MessageSystem.subscribe(MessageSystem.selectLocation, function (selectedLocationName) {
			self.showLocation(selectedLocationName);
		});
		MessageSystem.subscribe(MessageSystem.selectPerson, function (selectedPersonName) {
			self.showPerson(selectedPersonName);
		});
		MessageSystem.subscribe(MessageSystem.makeAjax, function (ajax) {
			self.makeAjax(ajax.url, ajax.data);
		});

		this.makeAjax(this.props.startUrl);
	},
	progressTurn: function () {
		this.makeAjax(this.props.progressTurnUrl);
	},
	progress10Turns: function () {
		this.makeAjax(this.props.progress10TurnsUrl);
	},
	progress100Turns: function () {
		this.makeAjax(this.props.progress100TurnsUrl);
	},
	makeAjax: function (url, data) {
		//console.log("making ajax");
		$.ajax({
			url: url,
			data: data,
			dataType: 'json',
			cache: false,
			success: function (data) {
				this.prepareState(data);
			}.bind(this),
			error: function (xhr, status, err) {
				console.log(this.props.url, status, err.toString());
			}.bind(this)
		});
	},
	prepareState: function (data) {
		//console.log("data: " + JSON.stringify(data));
		if (data.action != undefined) {
			//console.log("ajax response had action: "+data.action);
			this.setState({data: undefined, action: data.action, info: data.info});
		} else {
			//console.log("ajax response no action");
			this.setState({data: data, action: undefined, info: undefined});
		}
	},
	showLocationList: function () {
		this.setState({showLocationList: true, selectedLocationName: undefined, showPersonList: false});
	},
	showLocation: function (selectedLocationName) {
		this.setState({showLocationList: true, selectedLocationName: selectedLocationName, showPersonList: false});
	},
	closeLocationList: function () {
		this.setState({showLocationList: false});
	},
	showPersonList: function () {
		this.setState({showPersonList: true, selectedPersonName: undefined, showLocationList: false});
	},
	showPerson: function (selectedPersonName) {
		this.setState({showPersonList: true, selectedPersonName: selectedPersonName, showLocationList: false});
	},
	closePersonList: function () {
		this.setState({showPersonList: false});
	},
	render: function () {

		if (!this.state) {
			return (<div className="main">
				laodin
			</div>);
		}

		console.log("main render method: " + this.state.action);

		if (this.state.action != undefined) {
			console.log("main render method: " + this.state.info);
		}

		var disableProgress = this.state.action !== undefined;

		return (
			<div>
				<div>
					<button onClick={this.progressTurn} disabled={disableProgress}>Progress Turn</button>
					<button onClick={this.progress10Turns} disabled={disableProgress}>Progress 10 Turns</button>
					<button onClick={this.progress100Turns} disabled={disableProgress}>Progress 100 Turns</button>
					<button onClick={this.showLocationList}>Location list</button>
					<button onClick={this.showPersonList}>Person list</button>
				</div>

				{ this.state.data ? <Board data={this.state.data}/> : null }

				{ this.state.data ? <GameInfo data={this.state.data}/> : null }

				{ this.state.data && this.state.data.worldData ? <WorldStats className="WorldStats" data={this.state.data.worldData}/> : null }

				{ this.state.showLocationList ? <LocationDialog locations={this.state.data.locations}
																selectedLocationName={this.state.selectedLocationName}
																requestClose={this.closeLocationList}/> : null }

				{ this.state.showPersonList ? <PersonDialog persons={this.state.data.persons}
															selectedPersonName={this.state.selectedPersonName}
															requestClose={this.closePersonList}/> : null }

				{ this.state.action === "ProposalAction" ? <Dialog data={this.state.info}/> : null }
				{ this.state.action === "FightAction" ? <Fight data={this.state.info}/> : null }
			</div>



		);


	}
});

ReactDOM.render(
	<Main startUrl="http://localhost:8000/backend"
		  progressTurnUrl="http://localhost:8000/backend/progressTurn?turns=1"
		  progress10TurnsUrl="http://localhost:8000/backend/progressTurn?turns=10"
		  progress100TurnsUrl="http://localhost:8000/backend/progressTurn?turns=100"/>,
	document.getElementById('content')
);
