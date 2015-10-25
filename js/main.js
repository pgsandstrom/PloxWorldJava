var World = React.createClass({
	getInitialState: function () {
		return {data: {planets: []}};
	},
	componentDidMount: function () {
		console.log("gonna request");
		$.ajax({
			url: this.props.url,
			dataType: 'json',
			cache: false,
			success: function (data) {
				console.log("data: " + data);
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
			<div className="world">

				<p onClick={this.showPlanetList}>
					Planet list
				</p>

				<PlanetList data={this.state.data.planets}/>
				{ this.state.showPlanetList ? <ploxworld.PlanetDialog data={this.state.data.planets} requestClose={this.closePlanetList}/> : null }
			</div>

		);
	}
});
var PlanetList = React.createClass({
	render: function () {
		var planetNodes = this.props.data.map(function (planet) {
			return (
				<Planet key={planet.name} data={planet}>
				</Planet>
			);
		});
		return (
			<div className="planetList">
				{planetNodes}
			</div>
		);
	}
});
var Planet = React.createClass({
	render: function () {

		var divStyle = {
			'paddingLeft': this.props.data.point.x + 'px',
			'paddingTop': this.props.data.point.y + 'px'
		};

		return (
			<div className="planet" style={divStyle}>
				{this.props.data.name}
			</div>
		);
	}
});



ReactDOM.render(
	<World url="/backend"/>,
	document.getElementById('content')
);
