var ploxworld = window.ploxworld = window.ploxworld || {};

ploxworld.CivilizationInfo = React.createClass({
	render: function () {
		//console.log("CivilizationInfo render: " + JSON.stringify(this.props));
		return (
			<div>
					<span className="cell">
					Population
					</span>
				{Number(this.props.civilization.population) | 0 } / {this.props.civilization.maxPopulation}
			</div>
		);
	}
});