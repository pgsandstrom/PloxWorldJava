import React from 'react';

const GameInfo = React.createClass({
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
							{person.ai} : {person.name} : {person.money}
						</div>

					);
				})}
			</div>
		);
	}
});

export default GameInfo;