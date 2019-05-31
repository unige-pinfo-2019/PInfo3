import Vue from 'vue'
import App from './App.vue'
import router from './router'
import BootstrapVue from 'bootstrap-vue'
import Keycloak from 'keycloak-js'///dist/keycloak.js'
import store from './store.js'
// import store from 'plugin-vuejs-keycloak'

Vue.prototype.$store = store;

import { library } from '@fortawesome/fontawesome-svg-core'
import { faSearch } from '@fortawesome/free-solid-svg-icons'
import { faCamera } from '@fortawesome/free-solid-svg-icons'
import { faPlus } from '@fortawesome/free-solid-svg-icons'
import { faTrashAlt } from '@fortawesome/free-solid-svg-icons'
import { faEdit } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'

import '@/scss/_custom.scss'

library.add(faSearch)
library.add(faCamera)
library.add(faPlus)
library.add(faTrashAlt)
library.add(faEdit)
Vue.component('font-awesome-icon', FontAwesomeIcon)

Vue.use(BootstrapVue)

Vue.config.productionTip = false

// let initOptions = {
//   url: 'https://localhost:8080/auth', realm: '@static/keycloak', clientId: 'login-pinfo3', onLoad:'login-required'
// }
//
// let keycloak = Keycloak(initOptions);



var keycloak = Keycloak({
    url: 'http://localhost:8080/auth',
    realm: 'apigw',
    clientId: 'web-sso'
});

keycloak.init({ onLoad: 'login-required' }).success((auth) =>{

    if(!auth) {
      window.location.reload();
      console.log('Not authenticated');
    } else {
      // Vue.$log.info("Authenticated");
      console.log('Authenticated');
    }

    // new Vue({
    //   router,
    //   render: h => h(App),
    // }).$mount('#app')


    localStorage.setItem("vue-token", keycloak.token);
    // localStorage.setItem("vue-refresh-token", keycloak.refreshToken);

    // setTimeout(() =>{
    //   keycloak.updateToken(70).success((refreshed)=>{
    //     if (refreshed) {
    //       Vue.$log.debug('Token refreshed'+ refreshed);
    //     } else {
    //       Vue.$log.warn('Token not refreshed, valid for '
    //       + Math.round(keycloak.tokenParsed.exp + keycloak.timeSkew - new Date().getTime() / 1000) + ' seconds');
    //     }
    //   }).error(()=>{
    //       Vue.$log.error('Failed to refresh token');
    //   });
    //
    //
    // }, 60000)

}).error(() =>{
  Vue.$log.error("Authenticated Failed");
});

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
