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
					<span className="cell">
					Population
					</span>
					{Number(this.props.planet.population) | 0 } / {this.props.planet.maxPopulation}
				</div>
				<div>
					<span className="cell">
					Info
					</span>
					<span className="cell">
					Storage
					</span>
					<span className="cell">
					Workers
					</span>
					<span className="cell">
					Effectivity
					</span>
					<span className="cell">
					Buy
					</span>
					<span className="cell">
					Sell
					</span>
				</div>
				<div>
					<span className="cell">
					Money
					</span>
					<span className="cell">
					{this.props.planet.money}
					</span>
				</div>
				<div>
					<span className="cell">
					Commodity
					</span>
					<span className="cell">
					{this.props.planet.commodity.storage}
					</span>
					<span className="cell">
					{this.props.planet.commodity.workers}
					</span>
					<span className="cell">
					{this.props.planet.commodity.multiplier}
					</span>
					<span className="cell">
					{this.props.planet.commodity.buyPrice}
					</span>
					<span className="cell">
					{this.props.planet.commodity.sellPrice}
					</span>
				</div>
				<div>
					<span className="cell">
					Material
					</span>
					<span className="cell">
					{this.props.planet.material.storage}
					</span>
					<span className="cell">
					{this.props.planet.material.workers}
					</span>
					<span className="cell">
					{this.props.planet.material.multiplier}
					</span>
					<span className="cell">
					{this.props.planet.material.buyPrice}
					</span>
					<span className="cell">
					{this.props.planet.material.sellPrice}
					</span>
				</div>
				<div>
					<span className="cell">
					Construction
					</span>
					<span className="cell">
					{this.props.planet.construction.storage}
					</span>
					<span className="cell">
					{this.props.planet.construction.workers}
					</span>
					<span className="cell">
					{this.props.planet.construction.multiplier}
					</span>
					<span className="cell">
					{this.props.planet.construction.buyPrice}
					</span>
					<span className="cell">
					{this.props.planet.construction.sellPrice}
					</span>
				</div>
				<div>
					<span className="cell">
					Crystal
					</span>
					<span className="cell">
					{this.props.planet.crystal.storage}
					</span>
					<span className="cell">
					{this.props.planet.crystal.workers}
					</span>
					<span className="cell">
					{this.props.planet.crystal.multiplier}
					</span>
					<span className="cell">
					{this.props.planet.crystal.buyPrice}
					</span>
					<span className="cell">
					{this.props.planet.crystal.sellPrice}
					</span>
				</div>
				<div>
					<span className="cell">
					Science
					</span>
					<span className="cell">
					{this.props.planet.science.storage}
					</span>
					<span className="cell">
					{this.props.planet.science.workers}
					</span>
					<span className="cell">
					{this.props.planet.science.multiplier}
					</span>
					<span className="cell">
					{this.props.planet.science.buyPrice}
					</span>
					<span className="cell">
					{this.props.planet.science.sellPrice}
					</span>
				</div>
			</div>
		);
	}
});