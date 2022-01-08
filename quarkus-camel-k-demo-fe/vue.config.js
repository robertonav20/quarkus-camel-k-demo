module.exports = {
  runtimeCompiler: true,
  // NOTE: set alias via `configureWebpack` or `chainWebpack`
  configureWebpack: {
    resolve: {
      alias: {
        'balm-ui-plus': 'balm-ui/dist/balm-ui-plus.js',
        'balm-ui-css': 'balm-ui/dist/balm-ui.css'
      }
    }
  },
  pluginOptions: {},
  pages: {
    index: {
      entry: 'src/main.js',
      template: 'public/index.html',
      filename: 'index.html',
      title: 'Dashboard',
      chunks: ['chunk-vendors', 'chunk-common', 'index']
    }
  }
}
