import Vue from 'vue'
import VueImg from 'v-img';
import App from './App.vue'
import router from './router'
import BootstrapVue from 'bootstrap-vue'
import Keycloak from 'keycloak-js'
import axios from 'axios';

import { library } from '@fortawesome/fontawesome-svg-core'
import { faSearch, faCamera, faPlus, faTrashAlt, faEdit, faStar as fasStar, faTimes, faUser } from '@fortawesome/free-solid-svg-icons'
library.add(faSearch, faCamera, faPlus, faTrashAlt, faEdit, fasStar, faTimes, faUser)

import { faStar as farStar} from '@fortawesome/free-regular-svg-icons'
library.add(farStar)

import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'

import '@/scss/_custom.scss'

Vue.component('font-awesome-icon', FontAwesomeIcon)

Vue.use(BootstrapVue)
Vue.use(VueImg);


Vue.config.productionTip = false


var keycloak = Keycloak({
    url: process.env.VUE_APP_BASE_API + ':8080/auth',
    realm: 'apigw',
    clientId: 'web-sso'
});

Vue.prototype.$keycloak = keycloak;



Vue.prototype.$axios = axios;

Vue.prototype.$myStore = new Vue({
    data: {
       // token property returning the ls token value
       loggedIn: 'out',//window.localStorage.getItem('status'),
       username: null, //window.localStorage.getItem('username'),
       userid: null, //window.localStorage.getItem('userid'),
       refreshToken: null, //window.localStorage.getItem('refresh-token')
    },
    watch:{
       // watcher listening for changes on the token property
       // to ensure the new value is written back to ls
       loggedIn(value) {
         // window.localStorage.setItem('status', value)
         loggedIn = value
       },

       username(value) {
         // window.localStorage.setItem('username', value)
         username = value
       },

      userid(value) {
        // window.localStorage.setItem('userid', value)
        userid = value
      },

      refreshToken(value) {
        // window.localStorage.setItem('refresh-token', value)
        refreshToken = value
      }
    }
})

new Vue({
  router,
  // keycloak,
  render: h => h(App)
}).$mount('#app')
