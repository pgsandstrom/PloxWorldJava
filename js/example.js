// tutorial1.js
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
	render: function () {
		return (
			<div className="world">
				<PlanetList data={this.state.data.planets}/>
			</div>
		);
	}
});
var PlanetList = React.createClass({
	render: function () {
		var planetNodes = this.props.data.map(function (planet) {
			return (
				<Planet data={planet}>
					{planet.point.x}
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
			'padding-left': this.props.data.point.x + 'px',
			'padding-top': this.props.data.point.y + 'px'
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