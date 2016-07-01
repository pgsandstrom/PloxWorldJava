import React from 'react';

const ShipInfo = React.createClass({
	render: function () {
		// console.log("ShipInfo render: " + JSON.stringify(this.props));

		var storage = [];
		for (var item in this.props.ship.storage) {
			storage.push({type: item, amount: this.props.ship.storage[item]});
		}

		return (
			<div>
				<h2>Ship</h2>
				<div>
					Health: {this.props.ship.health} / {this.props.ship.maxHealth}
				</div>
				<div>
					Base: {this.props.ship.shipBase.name}
				</div>
				<div>
					Weapon: {this.props.ship.weapon.name}
				</div>

				{storage.map(function (storageItem) {
					return (
						<div key={storageItem.type}>
							<span className="cell">{storageItem.type}</span><span className="cell">{storageItem.amount}</span>
						</div>
					);
				})}
			</div>
		);
	}
});

export default ShipInfo;