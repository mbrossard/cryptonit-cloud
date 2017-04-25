const path = require('path');
const webpack = require('webpack');

const rules = [
    {
        test: /\.(js|jsx)$/,
        exclude: /node_modules/,
        loader: 'babel',
        query: {
            cacheDirectory: true,
            plugins: ['transform-runtime'],
            presets: ['es2015', 'react', 'stage-0'],
        }
    }, {
        test: /\.json$/,
        loader: 'json'
    }, {
        test: /\.scss$/,
        exclude: /\/src\/styles\//,
        loaders: [
            'style', [
                'css?sourceMap&-minimize',
                'modules',
                'importLoaders=1',
            ].join('&'),
            'postcss',
            'sass?sourceMap'
        ]
    }, {
        test: /\.scss$/,
        include: /\/src\/styles\//,
        loaders: [
            'style',
            'css?sourceMap&-minimize',
            'postcss',
            'sass?sourceMap'
        ]
    }, {
        test: /\.css$/,
        loaders: [
            'style', [
                'css?sourceMap&-minimize',
                'modules',
                'importLoaders=1',
            ].join('&'),
            'postcss'
        ]
    },
    { test: /\.woff(\?.*)?$/,  loader: 'url?prefix=fonts/&name=[path][name].[ext]&limit=10000&mimetype=application/font-woff' },
    { test: /\.woff2(\?.*)?$/, loader: 'url?prefix=fonts/&name=[path][name].[ext]&limit=10000&mimetype=application/font-woff2' },
    { test: /\.otf(\?.*)?$/,   loader: 'file?prefix=fonts/&name=[path][name].[ext]&limit=10000&mimetype=font/opentype' },
    { test: /\.ttf(\?.*)?$/,   loader: 'url?prefix=fonts/&name=[path][name].[ext]&limit=10000&mimetype=application/octet-stream' },
    { test: /\.eot(\?.*)?$/,   loader: 'file?prefix=fonts/&name=[path][name].[ext]' },
    { test: /\.svg(\?.*)?$/,   loader: 'url?prefix=fonts/&name=[path][name].[ext]&limit=10000&mimetype=image/svg+xml' },
    { test: /\.(png|jpg)$/,    loader: 'url?limit=8192' }
]

module.exports = {
    context: path.resolve(__dirname, '.'),
    name: 'client',
    target: 'web',
    devtool: 'source-map',
    resolve: {
        root: path.join(__dirname, './src'),
        extensions: ['', '.js', '.jsx', '.json'],
        modules: [
            path.resolve(__dirname, './node_modules'),
            path.resolve(__dirname, './src')
        ]
    },
    module: {
        loaders: rules
    },
    entry: {
        init: [path.join(__dirname, './src/init.js')],
        template: path.join(__dirname, './src/template.js'),
        app: [
            'babel-polyfill',
            path.join(__dirname, './src/main.js')
        ],
        vendor: [
            'history',
            'react',
            'react-redux',
            'react-router',
            'react-router-redux',
            'redux',
            'uuid',
            'react-bootstrap',
            'react-router-bootstrap'
        ]
    },
    output: {
        filename: "[name].[hash].js",
        path: path.join(__dirname, './dist'),
        publicPath: '/'
    }
}
