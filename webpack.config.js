
module.exports = {
	entry: './main.js',
	output: {path: __dirname, filename: 'bundle.js'},
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
			{ test: /\.css$/, loader: "style-loader!css-loader" }
		]
	}
};

//TODO: Denna verkar krävas för css-mecket. Se https://github.com/webpack/css-loader/issues/145
require('es6-promise').polyfill();