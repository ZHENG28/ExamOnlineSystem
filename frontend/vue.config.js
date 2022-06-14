module.exports = {
  // css、js、img静态资源存放文件夹
  // assetsDir: 'static',
  lintOnSave: false,

  devServer: {
    host: 'localhost',
    port: 8080,
    proxy: {
      '/api': {
        target: 'http://localhost:9090',
        // https协议
        // secure: false,
        changOrigin: true,
        pathRewrite: {
          '^/api': '',
        },
      },
    },
  },
}
