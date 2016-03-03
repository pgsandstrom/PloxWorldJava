var Main = React.createClass({
	componentDidMount: function () {
		var self = this;
		MessageSystem.subscribe(MessageSystem.selectLocation, function (selectedLocationName) {
			self.showLocation(selectedLocationName);
		});

		$.ajax({
			url: this.props.startUrl,
			dataType: 'json',
			cache: false,
			success: function (data) {
				console.log("data: " + JSON.stringify(data));
				this.setState({data: data});
			}.bind(this),
			error: function (xhr, status, err) {
				console.error(this.props.url, status, err.toString());
			}.bind(this)
		});
	},
	progressTurn: function () {
		$.ajax({
			url: this.props.progressTurnUrl,
			dataType: 'json',
			cache: false,
			success: function (data) {
				console.log("data: " + JSON.stringify(data));
				this.setState({data: data});
			}.bind(this),
			error: function (xhr, status, err) {
				console.error(this.props.url, status, err.toString());
			}.bind(this)
		});
	},
	showLocationList: function () {
		this.setState({showLocationList: true, selectedLocationName: undefined});
	},
	showLocation: function (selectedLocationName) {
		this.setState({showLocationList: true, selectedLocationName: selectedLocationName});
	},
	closeLocationList: function () {
		this.setState({showLocationList: false});
	},
	render: function () {

		if (!this.state) {
			return (<div className="main">
				laodin
			</div>);
		}

		return (
			<div className="main">
				<button onClick={this.progressTurn}>Progress Turn</button>
				<button onClick={this.showLocationList}>Location list</button>

				<ploxworld.Board data={this.state.data}></ploxworld.Board>

				<ploxworld.WorldStats className="WorldStats" data={this.state.data.worldData}></ploxworld.WorldStats>

				{ this.state.showLocationList ? <ploxworld.LocationDialog locations={this.state.data.locations}
																	  selectedLocationName={this.state.selectedLocationName}
																	  requestClose={this.closeLocationList}/> : null }
			</div>

		);
	}
});

ReactDOM.render(
	<Main startUrl="/backend" progressTurnUrl="/backend/progressTurn"/>,
	document.getElementById('content')
);
