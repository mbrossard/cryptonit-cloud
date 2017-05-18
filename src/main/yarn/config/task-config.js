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

const postcssLoader = {
  loader: 'postcss-loader',
  options: {
    config: {
      ctx: {
        cssnext: {},
        cssnano: {
          discardComments: {
            removeAll: true
          },
          discardUnused: false,
          mergeIdents: false,
          reduceIdents: false,
          safe: true,
          sourceMap: true
        },
        autoprefixer: {
            add: true,
            remove: true,
            browsers: ['last 2 versions']
        }
      }
    },
    sourceMap: true
  }
}

const sass_loaders = [
  'style-loader',
  cssModulesLoader,
  postcssLoader,
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
  postcssLoader
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
      },
      {
        test: /\.woff$/,
        loader: {
          loader: 'url-loader',
          options: {
            limit: 10000,
            mimetype: "application/font-woff",
            prefix:"fonts/",
            name:"[path][name].[ext]"
          }
        }
      },
      {
        test: /\.woff2$/,
        loader: {
          loader: 'url-loader',
          options: {
            limit: 10000,
            mimetype: "application/font-woff2",
            prefix:"fonts/",
            name:"[path][name].[ext]"
          }
        }
      },
      {
        test: /\.ttf$/,
        loader: {
          loader: 'url-loader',
          options: {
            limit: 10000,
            mimetype: "application/octet-stream",
            prefix:"fonts/",
            name:"[path][name].[ext]"
          }
        }
      },
      {
        test: /\.svg$/,
        loader: {
          loader: 'url-loader',
          options: {
            limit: 10000,
            mimetype: "image/svg+xml",
            prefix:"fonts/",
            name:"[path][name].[ext]"
          }
        }
      },
      {
        test: /\.otf$/,
        loader: {
          loader: 'file-loader',
          options: {
            limit: 10000,
            mimetype: "font/opentype",
            prefix:"fonts/",
            name:"[path][name].[ext]"
          }
        }
      },
      {
        test: /\.eot$/,
        loader: {
          loader: 'file-loader',
          options: {
            mimetype: "font/opentype",
            prefix:"fonts/"
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
