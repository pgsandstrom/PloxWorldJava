var ploxworld = window.ploxworld = window.ploxworld || {};

ploxworld.WorldStats = React.createClass({
	render: function () {
		return (
			<div>
				<h1>World Stats</h1>

				<div>
					Commodity: {this.props.data.totalProduction.commodity.storage}. {this.props.data.totalProduction.commodity.production}
				</div>
				<div>
					Material: {this.props.data.totalProduction.material.storage}. {this.props.data.totalProduction.material.production}
				</div>
				<div>
					Construction: {this.props.data.totalProduction.construction.storage}. {this.props.data.totalProduction.construction.production}
				</div>
				<div>
					Crystal: {this.props.data.totalProduction.crystal.storage}. {this.props.data.totalProduction.crystal.production}
				</div>
				<div>
					Science: {this.props.data.totalProduction.science.storage}. {this.props.data.totalProduction.science.production}
				</div>
			</div>
		);
	}
});