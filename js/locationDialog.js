var ploxworld = window.ploxworld = window.ploxworld || {};

ploxworld.LocationDialog = React.createClass({
	selectLocation: function (selectedLocationName) {
		this.setState({selectedLocationName: selectedLocationName});
	},
	getLocation: function (locationName) {
		return this.props.locations.find(function (element) {
			if (element.name == locationName) {
				return element;
			} else {
				return false;
			}
		});
	},
	componentDidMount: function () {
		var selectedLocationName = this.props.selectedLocationName;
		if (selectedLocationName !== undefined) {
			this.selectLocation(selectedLocationName);
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

					{this.props.locations.map(function (location) {
						return (
							<div key={location.name} onClick={self.selectLocation.bind(self, location.name)}>
								{location.name}
							</div>
						);
					})}
				</div>

				<div style={{float:'left'}}>
					{this.state != undefined && this.state.selectedLocationName != undefined ?
						<LocationDetails location={this.getLocation(this.state.selectedLocationName)}/> : 'Choose a location'}
				</div>
			</div>
		);
	}
});

var LocationDetails = React.createClass({
	render: function () {
		//console.log("locationDetails render: " + JSON.stringify(this.props));
		return (
			<div>
				<h1>{this.props.location.name}</h1>

				{this.props.location.civilization != undefined ?
					<ploxworld.CivilizationInfo civilization={this.props.location.civilization}/> : null
				}
				{this.props.location.tradeable != undefined ?
					<ploxworld.TradeableInfo tradeable={this.props.location.tradeable}/> : null
				}

			</div>
		);
	}
});