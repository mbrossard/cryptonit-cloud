const path = require('path');
const cssnano = require('cssnano');
const webpack = require('webpack');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const ExtractTextPlugin = require('extract-text-webpack-plugin');
const yargs = require('yargs');
const _debug = require('debug')
const ip = require('ip')

const debug = _debug('app:webpack:config')

const postcss = [
  cssnano({
    autoprefixer: {
      add: true,
      remove: true,
      browsers: ['last 2 versions']
    },
    discardComments: {
      removeAll: true
    },
    discardUnused: false,
    mergeIdents: false,
    reduceIdents: false,
    safe: true,
    sourcemap: true
  })
]

const config = {
  env : process.env.NODE_ENV || 'development',

  path_base  : path.resolve(__dirname, '.'),
  dir_src : 'src',
  dir_dist   : 'dist',
  dir_server : 'server',
  dir_test   : 'tests',

  server_host : ip.address(),
  server_port : process.env.PORT || 3000,

  compiler_css_modules     : true,
  compiler_devtool         : 'source-map',
  compiler_hash_type       : 'hash',
  compiler_fail_on_warning : false,
  compiler_quiet           : false,
  compiler_public_path     : '/',
  compiler_stats           : {
    chunks : false,
    chunkModules : false,
    colors : true
  },

  coverage_reporters : [
    { type : 'text-summary' },
    { type : 'lcov', dir : 'coverage' }
  ]
}

config.globals = {
  'process.env'  : {
    'NODE_ENV' : JSON.stringify(config.env)
  },
  'NODE_ENV'     : config.env,
  '__DEV__'      : config.env === 'development',
  '__PROD__'     : config.env === 'production',
  '__TEST__'     : config.env === 'test',
  '__DEBUG__'    : config.env === 'development' && !yargs.argv.no_debug,
  '__COVERAGE__' : !yargs.argv.watch && config.env === 'test',
  '__BASENAME__' : JSON.stringify(process.env.BASENAME || '')
}

const base = (...args) =>
  Reflect.apply(path.resolve, null, [config.path_base, ...args])

config.utils_paths = {
  base   : base,
  src : base.bind(null, config.dir_src),
  dist   : base.bind(null, config.dir_dist)
}

debug(`Environment overrides for NODE_ENV "${config.env}".`)
const environments = {
  development: (config) => ({
    compiler_public_path: `http://${config.server_host}:${config.server_port}/`,
    proxy: {
      enabled: false,
      options: {
        host: 'http://localhost:8000',
        match: /^\/api\/.*/
      }
    }
  }),
  production: (config) => ({
    compiler_public_path: '/',
    compiler_fail_on_warning: false,
    compiler_hash_type: 'chunkhash',
    compiler_devtool: null,
    compiler_stats: {
      chunks: true,
      chunkModules: true,
      colors: true
    }
  })
}
const overrides = environments[config.env]
if (overrides) {
  Object.assign(config, overrides(config))
}

const paths = config.utils_paths
const {__DEV__, __PROD__, __TEST__} = config.globals


const APP_ENTRY_PATHS = [
    'babel-polyfill',
    paths.src('main.js')
]

const css_loader = 'css?sourceMap&-minimize'
const css_module_paths = [ paths.src().replace(/[\^\$\.\*\+\-\?\=\!\:\|\\\/\(\)\[\]\{\}\,]/g, '\\$&') ]
const cssModulesRegex = new RegExp(`(${css_module_paths.join('|')})`)
const excludeCSSModules = cssModulesRegex

const cssModulesLoader = [
  css_loader,
  'modules',
  'importLoaders=1',
  'localIdentName=[name]__[local]___[hash:base64:5]'
].join('&')

const sass_loaders = [
  'style',
  cssModulesLoader,
  'postcss',
  'sass?sourceMap'
]

const css_loaders = [
  'style',
  cssModulesLoader,
  'postcss'
]

const loaders = [
  {
      test: /\.(js|jsx)$/,
      exclude: /node_modules/,
      loader: 'babel',
      query: {
        cacheDirectory: true,
        plugins: ['transform-runtime'],
        presets: ['es2015', 'react', 'stage-0'],
        env: {
          production: {
            //presets: ['react-optimize'] //Fails for react-bootstrap sub components
          }
        }
      }
  },
  {
    test: /\.json$/,
    loader: 'json'
  },
  {
    test: /\.scss$/,
    include: cssModulesRegex,
    exclude: /\/src\/styles\//,
    loaders: sass_loaders
  },
  {
    test: /\.scss$/,
    include: /\/src\/styles\//,
    loaders: sass_loaders
  },
  {
    test: /\.scss$/,
    exclude: excludeCSSModules,
    loaders: sass_loaders
  },
  {
    test: /\.css$/,
    include: cssModulesRegex,
    loaders: css_loaders
  },
  {
    test: /\.css$/,
    exclude: excludeCSSModules,
    loaders: [
      'style',
      css_loader,
      'postcss',
    ]
  },
  {
    test: /app\.min\.css$/,
    loader: 'string-replace',
    query: {
      search: 'body',
      replace: '#application',
      flags: 'g'
    }
  },
  { test: /\.woff(\?.*)?$/,  loader: 'url?prefix=fonts/&name=[path][name].[ext]&limit=10000&mimetype=application/font-woff' },
  { test: /\.woff2(\?.*)?$/, loader: 'url?prefix=fonts/&name=[path][name].[ext]&limit=10000&mimetype=application/font-woff2' },
  { test: /\.otf(\?.*)?$/,   loader: 'file?prefix=fonts/&name=[path][name].[ext]&limit=10000&mimetype=font/opentype' },
  { test: /\.ttf(\?.*)?$/,   loader: 'url?prefix=fonts/&name=[path][name].[ext]&limit=10000&mimetype=application/octet-stream' },
  { test: /\.eot(\?.*)?$/,   loader: 'file?prefix=fonts/&name=[path][name].[ext]' },
  { test: /\.svg(\?.*)?$/,   loader: 'url?prefix=fonts/&name=[path][name].[ext]&limit=10000&mimetype=image/svg+xml' },
  { test: /\.(png|jpg)$/,    loader: 'url?limit=8192' }
];

const plugins = [
  new webpack.DefinePlugin(config.globals),
  new HtmlWebpackPlugin({
    template: paths.src('index.html'),
    hash: false,
    filename: 'index.html',
    inject: 'body',
    minify: {
      collapseWhitespace: true
    }
  })
]

if (__DEV__) {
  debug('Enable plugins for live development (HMR, NoErrors).')
  plugins.push(
    new webpack.HotModuleReplacementPlugin(),
    new webpack.NoErrorsPlugin()
  )
} else if (__PROD__) {
  debug('Enable plugins for production (OccurenceOrder, Dedupe & UglifyJS).')
  plugins.push(
    new webpack.optimize.OccurrenceOrderPlugin(),
    new webpack.optimize.DedupePlugin(),
    new webpack.optimize.UglifyJsPlugin({
      compress: {
        unused: true,
        dead_code: true,
        warnings: false
      }
    })
  )
}
if (!__TEST__) {
  plugins.push(
    new webpack.optimize.CommonsChunkPlugin({
      names: ['vendor']
    })
  )
}
// when we don't know the public path (we know it only when HMR is enabled [in development]) we
// need to use the extractTextPlugin to fix this issue:
// http://stackoverflow.com/questions/34133808/webpack-ots-parsing-error-loading-fonts/34133809#34133809
if (!__DEV__) {
  debug('Apply ExtractTextPlugin to CSS loaders.')
  loaders.filter((loader) =>
    loader.loaders && loader.loaders.find((name) => /css/.test(name.split('?')[0]))
  ).forEach((loader) => {
    const [first, ...rest] = loader.loaders
    loader.loader = ExtractTextPlugin.extract(first, rest.join('!'))
    Reflect.deleteProperty(loader, 'loaders')
  })

  plugins.push(
    new ExtractTextPlugin('[name].[contenthash].css', {
      allChunks: true
    })
  )
}

debug('Create configuration.')
module.exports = {
  name: 'client',
  target: 'web',
  devtool: config.compiler_devtool,
  resolve: {
    root: paths.src(),
    extensions: ['', '.js', '.jsx', '.json'],
    modules: [
        path.resolve('./node_modules'),
        path.resolve('./src')
    ]
  },
  module: {
    loaders: loaders
  },
  entry: {
      init: [paths.src('init.js')],
      template: paths.src('style.js'),
      app: __DEV__ ? APP_ENTRY_PATHS.concat(`webpack-hot-middleware/client?path=${config.compiler_public_path}__webpack_hmr`) : APP_ENTRY_PATHS,
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
      ],
  },
  postcss: postcss,
  sassLoader: {
    includePaths: paths.src('styles')
  },
  output: {
    filename: `[name].[${config.compiler_hash_type}].js`,
    path: paths.dist(),
    publicPath: config.compiler_public_path
  },
  plugins: plugins
}
