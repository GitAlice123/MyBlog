const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  // 修改端口为7000
  devServer: {
    port: 7001,
  },

})
