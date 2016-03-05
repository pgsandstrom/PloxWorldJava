var ploxworld = window.ploxworld = window.ploxworld || {};

ploxworld.CivilizationInfo = React.createClass({
	render: function () {
		//console.log("CivilizationInfo render: " + JSON.stringify(this.props));
		return (
			<div>
				<div>
					<span className="cell">
						Population
					</span>
					<span className="cell">
						{Number(this.props.civilization.population) | 0 } / {this.props.civilization.maxPopulation}
					</span>
				</div>

				<div>
					<span className="cell">
						Weapons
					</span>
					{this.props.civilization.weapons.map(function (weapon) {
						return (
							<span key={weapon} className="cellWide">
								{weapon}
							</span>
						);
					})}
				</div>
			</div>
		);
	}
});