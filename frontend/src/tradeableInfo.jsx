import React from 'react';

const TradeableInfo = React.createClass({
	render: function () {
		//console.log("TradeableInfo render: " + JSON.stringify(this.props));
		return (
			<div>
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
			{this.props.tradeable.money}
		</span>
				</div>
				<div>
					<span className="cell">
					Commodity
					</span>
					<span className="cell">
					{this.props.tradeable.commodity.storage}
					</span>
					<span className="cell">
					{this.props.tradeable.commodity.workers}
					</span>
					<span className="cell">
					{this.props.tradeable.commodity.multiplier}
					</span>
					<span className="cell">
					{this.props.tradeable.commodity.buyPrice}
					</span>
					<span className="cell">
					{this.props.tradeable.commodity.sellPrice}
					</span>
				</div>
				<div>
					<span className="cell">
					Material
					</span>
					<span className="cell">
					{this.props.tradeable.material.storage}
					</span>
					<span className="cell">
					{this.props.tradeable.material.workers}
					</span>
					<span className="cell">
					{this.props.tradeable.material.multiplier}
					</span>
					<span className="cell">
					{this.props.tradeable.material.buyPrice}
					</span>
					<span className="cell">
					{this.props.tradeable.material.sellPrice}
					</span>
				</div>
				<div>
					<span className="cell">
					Construction
					</span>
					<span className="cell">
					{this.props.tradeable.construction.storage}
					</span>
					<span className="cell">
					{this.props.tradeable.construction.workers}
					</span>
					<span className="cell">
					{this.props.tradeable.construction.multiplier}
					</span>
					<span className="cell">
					{this.props.tradeable.construction.buyPrice}
					</span>
					<span className="cell">
					{this.props.tradeable.construction.sellPrice}
					</span>
				</div>
				<div>
					<span className="cell">
					Crystal
					</span>
					<span className="cell">
					{this.props.tradeable.crystal.storage}
					</span>
					<span className="cell">
					{this.props.tradeable.crystal.workers}
					</span>
					<span className="cell">
					{this.props.tradeable.crystal.multiplier}
					</span>
					<span className="cell">
					{this.props.tradeable.crystal.buyPrice}
					</span>
					<span className="cell">
					{this.props.tradeable.crystal.sellPrice}
					</span>
				</div>
				<div>
					<span className="cell">
					Science
					</span>
					<span className="cell">
					{this.props.tradeable.science.storage}
					</span>
					<span className="cell">
					{this.props.tradeable.science.workers}
					</span>
					<span className="cell">
					{this.props.tradeable.science.multiplier}
					</span>
					<span className="cell">
					{this.props.tradeable.science.buyPrice}
					</span>
					<span className="cell">
					{this.props.tradeable.science.sellPrice}
					</span>
				</div>
			</div>
		);
	}
});

export default TradeableInfo;