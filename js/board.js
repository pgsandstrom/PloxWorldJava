var ploxworld = window.ploxworld = window.ploxworld || {};

ploxworld.Board = React.createClass({
	render: function () {

		var style = {
			'height': this.props.data.height + 'px',
			'width': this.props.data.width + 'px'
		};

		return (
			<div className="board" style={style}>
				<PlanetList data={this.props.data.planets}/>
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