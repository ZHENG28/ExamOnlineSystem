module.exports = {
  //   publicPath: '/', // 基本路径
  //   assetsDir: 'static', // css、js、img静态资源存放文件夹
  lintOnSave: false, // 是否在保存的时候使用 `eslint-loader` 进行检查

  devServer: {
    host: 'localhost',
    port: 8080,
    proxy: {
      // 配置代理表
      '/api': {
        target: 'http://localhost:9090', // 接口域名
        // secure: false, // https协议
        changOrigin: true,
        pathRewrite: {
          // 路径重写
          '^/api': '',
        },
      },
    },
  },
}
