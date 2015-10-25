var Main = React.createClass({
	getInitialState: function () {
		return {data: {height: 500, width: 500, planets: []}};
	},
	componentDidMount: function () {
		$.ajax({
			url: this.props.url,
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
	showPlanetList: function () {
		this.setState({showPlanetList: true});
	},
	closePlanetList: function () {
		this.setState({showPlanetList: false});
	},
	render: function () {

		return (
			<div className="main">
				<p onClick={this.showPlanetList}>
					Planet list
				</p>

				<ploxworld.Board url={this.props.url} data={this.state.data}></ploxworld.Board>

				{ this.state.showPlanetList ? <ploxworld.PlanetDialog data={this.state.data.planets} requestClose={this.closePlanetList}/> : null }
			</div>

		);
	}
});

ReactDOM.render(
	<Main url="/backend"/>,
	document.getElementById('content')
);
