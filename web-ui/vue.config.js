// vue.config.js

module.exports = {
  devServer: {
    port: 8086,
    disableHostCheck: true
  },
  css: {
    loaderOptions: {
      sass: {
        data: `
          @import "@/scss/_variables.scss";
        `
      }
    }
  }
}
