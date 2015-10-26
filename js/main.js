var Main = React.createClass({
	getInitialState: function () {
		return {data: {height: 500, width: 500, planets: []}};
	},
	componentDidMount: function () {
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
				console.log("data: " + JSON.stringify(data));
				this.setState({data: data});
			}.bind(this),
			error: function (xhr, status, err) {
				console.error(this.props.url, status, err.toString());
			}.bind(this)
		});
	},
	showPlanetList: function () {
		this.setState({showPlanetList: true});
	},
	closePlanetList: function () {
		this.setState({showPlanetList: false});
	},
	render: function () {

		return (
			<div className="main">
				<button onClick={this.progressTurn}>Progress Turn</button><button onClick={this.showPlanetList}>Planet list</button>

				<ploxworld.Board data={this.state.data}></ploxworld.Board>

				{ this.state.showPlanetList ? <ploxworld.PlanetDialog data={this.state.data.planets} requestClose={this.closePlanetList}/> : null }
			</div>

		);
	}
});

ReactDOM.render(
	<Main startUrl="/backend" progressTurnUrl="/backend/progressTurn"/>,
	document.getElementById('content')
);
