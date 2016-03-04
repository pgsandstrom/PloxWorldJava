var ploxworld = window.ploxworld = window.ploxworld || {};

ploxworld.GameInfo = React.createClass({
	render: function () {
		//console.log("GameInfo render: " + JSON.stringify(this.props));

		this.props.data.persons.sort(function (a, b) {
			return b.money - a.money;
		});

		return (
			<div className="gameInfo">
				<h2>Top list</h2>
				{this.props.data.persons.map(function (person) {
					return (
						<div key={person.name}>
							{person.name} : {person.money}
						</div>

					);
				})}
			</div>
		);
	}
});