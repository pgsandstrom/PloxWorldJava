var World = React.createClass({
	getInitialState: function () {
		return {data: {height: 500, width: 500, planets: []}};
	},
	componentDidMount: function () {
		$.ajax({
			url: this.props.url,
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

		var style = {
			'height': this.state.data.height + 'px',
			'width': this.state.data.width + 'px'
		};

		return (
			<div className="world" style={style}>

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

		var style = {
			'left': (this.props.data.point.x - 15) + 'px',
			'top': (this.props.data.point.y - 15) + 'px'
		};

		return (
			<div className="planet" style={style}>
				<img src="img/planet.png"/>
				<span>
					{this.props.data.name}
				</span>
			</div>
		);
	}
});


ReactDOM.render(
	<World className="world" url="/backend"/>,
	document.getElementById('content')
);
