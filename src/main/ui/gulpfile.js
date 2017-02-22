var gulp = require('gulp');
var sass = require('gulp-sass');
var sourcemaps = require('gulp-sourcemaps');
var uglify = require('gulp-uglifyjs');
var browserify = require('browserify');
var babelify = require('babelify');
var source = require('vinyl-source-stream');
var rimraf = require('rimraf');

var config = {
  publicDir: './../../../target/classes/webapp'
};

gulp.task('clean', function(cb) {
  rimraf('assets', cb);
});

gulp.task('css', function() {
    return gulp.src('css/style.scss')
    .pipe(sourcemaps.init())
    .pipe(sass({
        debug_info: true,
        outputStyle: 'compressed',
        includePaths: [
            './node_modules/bootstrap-sass/assets/stylesheets',
            './node_modules/font-awesome/scss',
            './css/theme'
        ]
    }))
    .pipe(sourcemaps.write())
    .pipe(gulp.dest(config.publicDir + '/css'));
});

gulp.task('js', function() {
    return browserify({
        entries: './js/main.js',
        debug: true
  	})
    .transform("babelify", {presets: ["es2015", "react"]})
	.bundle()
	.pipe(source('bundle.js'))
	.pipe(gulp.dest(config.publicDir + '/js'));
});

gulp.task('vendor', function() {
    return gulp.src([
        './node_modules/jquery/dist/jquery.js',
        './node_modules/jquery-ui/jquery-ui.js',
        './node_modules/bootstrap-sass/assets/javascripts/bootstrap.js',
        './node_modules/react/react.js',
        './node_modules/react/react-dom.js'
    ])
    .pipe(uglify('vendor.bundle.js', {
        compress: false,
        outSourceMap: true
    }))
    .pipe(gulp.dest(config.publicDir + '/js'));
});

gulp.task('fonts', function() {
  return gulp.src([
    './node_modules/bootstrap-sass/assets/fonts/**/*',
    './node_modules/font-awesome/fonts/*'
  ])
  .pipe(gulp.dest(config.publicDir + '/fonts'));
});

gulp.task('watch', function() {
  gulp.watch('src/css/**/*.scss', ['css']);
  gulp.watch('src/js/**/*.js', ['js']);
});

gulp.task('default', ['css', 'js', 'vendor', 'fonts']);
