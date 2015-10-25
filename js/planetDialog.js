var ploxworld = window.ploxworld = window.ploxworld || {};

ploxworld.PlanetDialog = React.createClass({
	render: function () {
		var planetNodes = this.props.data.map(function (planet) {
			return (
				<PlanetDialogItem key={planet.name} data={planet}>
				</PlanetDialogItem>
			);
		});
		return (
			<div className="planetDialog dialog">
				<button onClick={this.props.requestClose}>
					close
				</button>


				{planetNodes}
			</div>
		);
	}
});

var PlanetDialogItem = React.createClass({
	render: function () {
		return (
			<div>
				{this.props.data.name}
			</div>
		);
	}
});