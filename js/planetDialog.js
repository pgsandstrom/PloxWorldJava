var ploxworld = window.ploxworld = window.ploxworld || {};

ploxworld.PlanetDialog = React.createClass({
	selectPlanet: function (planet) {
		this.setState({planet: planet});
	},
	render: function () {
		var self = this;
		return (
			<div className="planetDialog dialog">
				<div style={{float:'left'}}>
					<button onClick={this.props.requestClose}>
						close
					</button>

					{this.props.data.map(function (planet) {
						return (
							<div key={planet.name} onClick={self.selectPlanet.bind(self, planet)}>
								{planet.name}
							</div>
						);
					})}
				</div>

				<div style={{float:'left'}}>
					{this.state != undefined ? <PlanetDetails planet={this.state.planet}></PlanetDetails> : 'Choose a planet'}
				</div>
			</div>
		);
	}
});

var PlanetDetails = React.createClass({
	render: function () {
		return (
			<div>
				<h1>{this.props.planet.name}</h1>

				Population: {Number(this.props.planet.population).toFixed(2)} / {this.props.planet.maxPopulation}
			</div>
		);
	}
});