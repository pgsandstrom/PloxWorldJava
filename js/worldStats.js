var ploxworld = window.ploxworld = window.ploxworld || {};

ploxworld.WorldStats = React.createClass({
	render: function () {
		console.log("WorldStats render: " + JSON.stringify(this.props));

		var data = [];
		for (var item in this.props.data.data) {
			data.push({type: item, amount: this.props.data.data[item]});
		}


		return (
			<div className="worldStats">
				<h1>World Stats</h1>

				<div>
					<div>
						<span className="cellWide">Commodity:</span>
						<span className="cell">{this.props.data.productionData.commodity.storage}</span>
						<span className="cell">{this.props.data.productionData.commodity.production}</span>
					</div>
					<div>
						<span className="cellWide">Material</span>
						<span className="cell">{this.props.data.productionData.material.storage}</span>
						<span className="cell">{this.props.data.productionData.material.production}</span>
					</div>
					<div>
						<span className="cellWide">Construction</span>
						<span className="cell">{this.props.data.productionData.construction.storage}</span>
						<span className="cell">{this.props.data.productionData.construction.production}</span>
					</div>
					<div>
						<span className="cellWide">Crystal</span>
						<span className="cell">{this.props.data.productionData.crystal.storage}</span>
						<span className="cell">{this.props.data.productionData.crystal.production}</span>
					</div>
					<div>
						<span className="cellWide">Science</span>
						<span className="cell">{this.props.data.productionData.science.storage}</span>
						<span className="cell">{this.props.data.productionData.science.production}</span>
					</div>
				</div>

				<div>
					{data.map(function (dataItem) {
						return (
							<div key={dataItem.type}>
								<span className="cellWide">{dataItem.type}</span>
								<span className="cell">{dataItem.amount}</span>
							</div>
						);
					})}
				</div>
			</div>
		);
	}
});