var ploxworld = window.ploxworld = window.ploxworld || {};

ploxworld.PlanetDialog = React.createClass({
	selectPlanet: function (selectedPlanetName) {
		console.log("selected planet: "+ JSON.stringify(selectedPlanetName));
		this.setState({selectedPlanetName: selectedPlanetName});
	},
	getPlanet: function (planetName) {
		console.log("getPlanet: "+JSON.stringify(planetName));
		return this.props.planets.find(function (element) {
			if (element.name == planetName) {
				return element;
			} else {
				return false;
			}
		});
	},
	componentDidMount: function () {
		var selectedPlanetName = this.props.selectedPlanetName;
		if (selectedPlanetName !== undefined) {
			this.selectPlanet(selectedPlanetName);
		}
	},
	render: function () {
		var self = this;
		return (
			<div className="planetDialog dialog">
				<div style={{float:'left'}}>
					<button onClick={this.props.requestClose}>
						close
					</button>

					{this.props.planets.map(function (planet) {
						return (
							<div key={planet.name} onClick={self.selectPlanet.bind(self, planet.name)}>
								{planet.name}
							</div>
						);
					})}
				</div>

				<div style={{float:'left'}}>
					{this.state != undefined && this.state.selectedPlanetName != undefined ?
						<PlanetDetails planet={this.getPlanet(this.state.selectedPlanetName)}></PlanetDetails> : 'Choose a planet'}
				</div>
			</div>
		);
	}
});

var PlanetDetails = React.createClass({
	render: function () {
		console.log("planetDetails render: " + JSON.stringify(this.props));
		return (
			<div>
				<h1>{this.props.planet.name}</h1>

				Population: {Number(this.props.planet.population).toFixed(2)} / {this.props.planet.maxPopulation}
			</div>
		);
	}
});