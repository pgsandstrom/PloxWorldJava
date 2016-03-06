var ploxworld = window.ploxworld = window.ploxworld || {};

ploxworld.ShipInfo = React.createClass({
	render: function () {
		//console.log("ShipInfo render: " + JSON.stringify(this.props));

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
					Base: {this.props.ship.shipBase}
				</div>
				<div>
					Weapon: {this.props.ship.weapon}
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