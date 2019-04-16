// vue.config.js
module.exports = {
  devServer: {
    port: 8081
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
