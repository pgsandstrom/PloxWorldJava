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
					Commodity: {this.props.planet.commodity}. {this.props.planet.commodityWorkers} * {this.props.planet.commodityMultiplier}
				</div>
				<div>
					Material: {this.props.planet.material}. {this.props.planet.materialWorkers} * {this.props.planet.materialMultiplier}
				</div>
				<div>
					Production: {this.props.planet.production}. {this.props.planet.productionWorkers} * {this.props.planet.productionMultiplier}
				</div>
				<div>
					Crystal: {this.props.planet.crystal}. {this.props.planet.crystalWorkers} * {this.props.planet.crystalMultiplier}
				</div>
				<div>
					Science: {this.props.planet.science}. {this.props.planet.scienceWorkers} * {this.props.planet.scienceMultiplier}
				</div>
			</div>
		);
	}
});