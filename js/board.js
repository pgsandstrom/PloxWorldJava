var ploxworld = window.ploxworld = window.ploxworld || {};

ploxworld.Board = React.createClass({
	render: function () {

		var style = {
			'height': this.props.data.height + 'px',
			'width': this.props.data.width + 'px'
		};

		return (
			<div className="board" style={style}>
				{this.props.data.planets.map(function (planet) {
					return (
							<Planet key={planet.name} data={planet}>
							</Planet>
					);
				})}
				{this.props.data.asteroids.map(function (asteroid) {
					return (
							<Asteroid key={asteroid.name} data={asteroid}>
							</Asteroid>
					);
				})}
				{this.props.data.persons.map(function (person) {
					return (
						<Person key={person.name} data={person}>
						</Person>
					);
				})}
			</div>

		);
	}
});

var Planet = React.createClass({
	showPlanet: function () {
		MessageSystem.dispatch(MessageSystem.selectPlanet, this.props.data.name);
	},
	render: function () {
		var style = {
			'left': (this.props.data.point.x - 15) + 'px',
			'top': (this.props.data.point.y - 15) + 'px'
		};

		return (
				<div className="planet" style={style} onClick={this.showPlanet}>
					<img src="img/planet.png"/>
				<span>
					{this.props.data.name}
				</span>
				</div>
		);
	}
});

var Asteroid = React.createClass({
	showAsteroid: function () {
		MessageSystem.dispatch(MessageSystem.selectAsteroid, this.props.data.name);
	},
	render: function () {
		var style = {
			'left': (this.props.data.point.x - 15) + 'px',
			'top': (this.props.data.point.y - 15) + 'px'
		};

		return (
				<div className="asteroid" style={style} onClick={this.showAsteroid}>
					<img src="img/asteroid.png"/>
				<span>
					{this.props.data.name}
				</span>
				</div>
		);
	}
});

var Person = React.createClass({
	showPerson: function () {
		//MessageSystem.dispatch(MessageSystem.selectPlanet, this.props.data.name);
	},
	render: function () {
		var style = {
			'left': (this.props.data.point.x - 5) + 'px',
			'top': (this.props.data.point.y - 6) + 'px'
		};

		return (
			<div className="person" style={style} onClick={this.showPerson}>
				<img src="img/ship_ai.png"/>
			</div>
		);
	}
});