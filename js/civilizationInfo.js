import React from 'react';
import ReactDOM from 'react-dom';

const CivilizationInfo = React.createClass({
	render: function () {
		console.log("CivilizationInfo render: " + JSON.stringify(this.props));
		this.props.civilization.weapons.map(function (weapon) {
			console.log("weapon: " + weapon);
		});
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
							<span key={weapon.name} className="cellWide">
								{weapon.name}
							</span>
						);
					})}
				</div>
			</div>
		);
	}
});

export default CivilizationInfo;