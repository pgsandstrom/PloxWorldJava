var ploxworld = window.ploxworld = window.ploxworld || {};

ploxworld.PlanetDialog = React.createClass({
	selectPlanet: function (selectedPlanetName) {
		this.setState({selectedPlanetName: selectedPlanetName});
	},
	getPlanet: function (planetName) {
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
		//console.log("planetDetails render: " + JSON.stringify(this.props));
		return (
			<div>
				<h1>{this.props.planet.name}</h1>

				<div>
					Population: {Number(this.props.planet.population).toFixed(2)} / {this.props.planet.maxPopulation}
				</div>
				<div>
					Money: {this.props.planet.money}
				</div>
				<div>
					Commodity: {this.props.planet.commodity.storage}. {this.props.planet.commodity.workers} * {this.props.planet.commodity.multiplier}
				</div>
				<div>
					Material: {this.props.planet.material.storage}. {this.props.planet.material.workers} * {this.props.planet.material.multiplier}
				</div>
				<div>
					Construction: {this.props.planet.construction.storage}. {this.props.planet.construction.workers} * {this.props.planet.construction.multiplier}
				</div>
				<div>
					Crystal: {this.props.planet.crystal.storage}. {this.props.planet.crystal.workers} * {this.props.planet.crystal.multiplier}
				</div>
				<div>
					Science: {this.props.planet.science.storage}. {this.props.planet.science.workers} * {this.props.planet.science.multiplier}
				</div>
			</div>
		);
	}
});