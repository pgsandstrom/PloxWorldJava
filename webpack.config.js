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
			}
		]
	}
};