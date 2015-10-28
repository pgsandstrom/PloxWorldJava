var Main = React.createClass({
	getInitialState: function () {
		return {data: {height: 500, width: 500, planets: []}};
	},
	componentDidMount: function () {
		var self = this;
		MessageSystem.subscribe(MessageSystem.selectPlanet, function (selectedPlanetName) {
			self.showPlanetList(selectedPlanetName);
		});

		$.ajax({
			url: this.props.startUrl,
			dataType: 'json',
			cache: false,
			success: function (data) {
				console.log("data: " + JSON.stringify(data));
				this.setState({data: data});
			}.bind(this),
			error: function (xhr, status, err) {
				console.error(this.props.url, status, err.toString());
			}.bind(this)
		});
	},
	progressTurn: function () {
		$.ajax({
			url: this.props.progressTurnUrl,
			dataType: 'json',
			cache: false,
			success: function (data) {
				//console.log("data: " + JSON.stringify(data));
				this.setState({data: data});
			}.bind(this),
			error: function (xhr, status, err) {
				console.error(this.props.url, status, err.toString());
			}.bind(this)
		});
	},
	showPlanetList: function (selectedPlanetName) {
		this.setState({showPlanetList: true, selectedPlanetName: selectedPlanetName});
	},
	closePlanetList: function () {
		this.setState({showPlanetList: false});
	},
	render: function () {

		return (
			<div className="main">
				<button onClick={this.progressTurn}>Progress Turn</button>
				<button onClick={this.showPlanetList}>Planet list</button>

				<ploxworld.Board data={this.state.data}></ploxworld.Board>

				{ this.state.showPlanetList ? <ploxworld.PlanetDialog planets={this.state.data.planets}
																	  selectedPlanetName={this.state.selectedPlanetName}
																	  requestClose={this.closePlanetList}/> : null }
			</div>

		);
	}
});

ReactDOM.render(
	<Main startUrl="/backend" progressTurnUrl="/backend/progressTurn"/>,
	document.getElementById('content')
);
