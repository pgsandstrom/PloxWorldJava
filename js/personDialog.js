var ploxworld = window.ploxworld = window.ploxworld || {};

ploxworld.PersonDialog = React.createClass({
	selectPerson: function (selectedPersonName) {
		this.setState({selectedPersonName: selectedPersonName});
	},
	getPerson: function (personName) {
		return this.props.persons.find(function (element) {
			if (element.name == personName) {
				return element;
			} else {
				return false;
			}
		});
	},
	componentDidMount: function () {
		console.log("componentDidMount");
		var selectedPersonName = this.props.selectedPersonName;
		if (selectedPersonName !== undefined) {
			this.selectPerson(selectedPersonName);
		}
	},
	render: function () {
		var self = this;
		return (
			<div className="dialog">
				<div style={{float:'left'}}>
					<button onClick={this.props.requestClose}>
						close
					</button>

					{this.props.persons.map(function (person) {
						return (
							<div key={person.name} onClick={self.selectPerson.bind(self, person.name)}>
								{person.name}
							</div>
						);
					})}
				</div>

				<div style={{float:'left'}}>
					{this.state != undefined && this.state.selectedPersonName != undefined ?
						<PersonDetails person={this.getPerson(this.state.selectedPersonName)}/> : 'Choose a person'}
				</div>
			</div>
		);
	}
});

var PersonDetails = React.createClass({
	render: function () {
		//console.log("personDetails render: " + JSON.stringify(this.props));
		return (
			<div>
				<h1>{this.props.person.name}</h1>

				<div>Money: {this.props.person.money}</div>

				{this.props.person.ship != undefined ?
					<ploxworld.ShipInfo ship={this.props.person.ship}/> : null
				}

			</div>
		);
	}
});