var webpack = require('webpack');
var path = require('path');

module.exports = {
	entry: './main.js',
	output: {path: path.join(__dirname, '/dist/'), filename: 'bundle_prod.js'},
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
	},
	plugins: [
		// Suggested in http://stackoverflow.com/a/31042143/249871
		new webpack.DefinePlugin({
			'process.env': {
				// This has effect on the react lib size
				'NODE_ENV': JSON.stringify('production')
			}
		}),
		// this makes the -p flag redundant. Optimized and minimizes
		new webpack.optimize.UglifyJsPlugin({
			compress:{
				warnings: true
			}
		})
	],
	// This could in theory decrease size, but currently does not work:
	// devtool: "source-map"

	"babel": {
		// https://babeljs.io/docs/plugins/transform-react-inline-elements/
		// https://babeljs.io/docs/plugins/transform-react-constant-elements/
		"plugins": ["transform-react-inline-elements", "transform-react-constant-elements"]

	}
};