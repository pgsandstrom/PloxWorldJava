var path = require('path');

module.exports = {
	entry: './main.js',
	output: {path: path.join(__dirname, '/dist/'), filename: 'bundle_dev.js'},
	module: {
		loaders: [
			{
				// Why does this test-line work even with jsx instead of js?
				test: /.js?$/,
				loader: 'babel-loader',
				exclude: /node_modules/,
				query: {
					presets: ['es2015', 'react']
				}
			},
			{test: /\.css$/, loader: "style-loader!css-loader"},
			{test: /\.png$/, loader: "url-loader?mimetype=image/png"}
		]
	}
};