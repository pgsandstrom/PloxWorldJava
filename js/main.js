import React from 'react';
import ReactDOM from 'react-dom';
import $ from 'jquery';

import MessageSystem from './messageSystem.js';
import Board from './board.js';
import GameInfo from './gameInfo.js';
import WorldStats from './worldStats.js';
import LocationDialog from './locationDialog.js';
import PersonDialog from './personDialog.js';


var Main = React.createClass({
	componentDidMount: function () {
		var self = this;
		MessageSystem.subscribe(MessageSystem.selectLocation, function (selectedLocationName) {
			self.showLocation(selectedLocationName);
		});
		MessageSystem.subscribe(MessageSystem.selectPerson, function (selectedPersonName) {
			self.showPerson(selectedPersonName);
		});

		$.ajax({
			url: this.props.startUrl,
			dataType: 'json',
			cache: false,
			success: function (data) {
				//console.log("data: " + JSON.stringify(data));
				this.setState({data: data});
			}.bind(this),
			error: function (xhr, status, err) {
				console.log(this.props.startUrl + "," + status + "," + err.toString());
			}.bind(this)
		});
	},
	progressTurn: function () {
		this.progress(this.props.progressTurnUrl);
	},
	progress10Turns: function () {
		this.progress(this.props.progress10TurnsUrl);
	},
	progress100Turns: function () {
		this.progress(this.props.progress100TurnsUrl);
	},
	progress: function (url) {
		$.ajax({
			url: url,
			dataType: 'json',
			cache: false,
			success: function (data) {
				//console.log("data: " + JSON.stringify(data));
				this.setState({data: data});
			}.bind(this),
			error: function (xhr, status, err) {
				console.log(this.props.url, status, err.toString());
			}.bind(this)
		});
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

		//console.log("this.state.data: "+JSON.stringify(this.state.data));

		return (
			<div>
				<div>
					<button onClick={this.progressTurn}>Progress Turn</button>
					<button onClick={this.progress10Turns}>Progress 10 Turns</button>
					<button onClick={this.progress100Turns}>Progress 100 Turns</button>
					<button onClick={this.showLocationList}>Location list</button>
					<button onClick={this.showPersonList}>Person list</button>
				</div>

				<Board data={this.state.data}/>

				<GameInfo data={this.state.data}/>

				<WorldStats className="WorldStats" data={this.state.data.worldData}/>

				{ this.state.showLocationList ? <LocationDialog locations={this.state.data.locations}
																		  selectedLocationName={this.state.selectedLocationName}
																		  requestClose={this.closeLocationList}/> : null }

				{ this.state.showPersonList ? <PersonDialog persons={this.state.data.persons}
																	  selectedPersonName={this.state.selectedPersonName}
																	  requestClose={this.closePersonList}/> : null }
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
