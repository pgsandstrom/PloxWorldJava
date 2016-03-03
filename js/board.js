var ploxworld = window.ploxworld = window.ploxworld || {};

ploxworld.Board = React.createClass({
	render: function () {

		var style = {
			'height': this.props.data.height + 'px',
			'width': this.props.data.width + 'px'
		};

		return (
			<div className="board" style={style}>
				{this.props.data.locations.map(function (location) {
					return (
						<Location key={location.name} data={location}>
						</Location>
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

var Location = React.createClass({
	showLocation: function () {
		MessageSystem.dispatch(MessageSystem.selectLocation, this.props.data.name);
	},
	render: function () {
		var style = {
			'left': (this.props.data.point.x - 15) + 'px',
			'top': (this.props.data.point.y - 15) + 'px'
		};

		var image;
		if (this.props.data.locationStyle == "PLANET") {
			image = 'img/planet.png'
		} else if (this.props.data.locationStyle == "ASTEROID") {
			image = 'img/asteroid.png'
		} else {
			image = 'img/BUGG.png'
		}

		return (
			<div className="location" style={style} onClick={this.showLocation}>
				<img src={image}/>
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