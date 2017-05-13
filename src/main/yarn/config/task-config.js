const cssModulesLoader = {
  loader: 'css-loader',
  options: {
    sourceMap: true,
    minimize: false,
    modules: true,
    importLoaders: 1,
    localIdentName: '[name]__[local]___[hash:base64:5]'
  }
}

const sass_loaders = [
  'style-loader',
  cssModulesLoader,
  {
    loader: 'sass-loader',
    options: {
      sourceMap: true
    }
  }
]

const css_loaders = [
  'style-loader',
  cssModulesLoader,
]

module.exports = {
  html        : true,
  images      : true,
  fonts       : true,
  static      : false,
  svgSprite   : false,
  ghPages     : false,
  stylesheets : true,

  javascripts: {
    extensions: ["js", "json", "scss", "jpg", "png"],

    customizeWebpackConfig: function (webpackConfig, env, webpack) {
      console.log(webpackConfig)
      return webpackConfig
    },

    extractSharedJs: false,

    entry: {
      app: ["./app.js"]
    },

    loaders: [
      {
        test: /\.scss$/,
        loader: sass_loaders
      },
      {
        test: /\.css$/,
        loader: css_loaders
      },
      {
        test: /\.json$/,
        loader: 'json-loader'
      },
      {
        test: /\.(png|jpg)$/,
        loader: {
          loader: 'url-loader',
          options: {
            limit: 8192
          }
        }
      }
    ]
  },

  browserSync: {
    server: {
      // should match `dest` in
      // path-config.json
      baseDir: 'public'
    }
  },

  production: {
    rev: true
  }
}
