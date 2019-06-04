import Vue from 'vue'
import App from './App.vue'
import router from './router'
import BootstrapVue from 'bootstrap-vue'
import Keycloak from 'keycloak-js'///dist/keycloak.js'
// import VueGlobalVariable from 'vue-global-var'
// import store from 'plugin-vuejs-keycloak'

import { library } from '@fortawesome/fontawesome-svg-core'
import { faSearch } from '@fortawesome/free-solid-svg-icons'
import { faCamera } from '@fortawesome/free-solid-svg-icons'
import { faPlus } from '@fortawesome/free-solid-svg-icons'
import { faTrashAlt } from '@fortawesome/free-solid-svg-icons'
import { faEdit } from '@fortawesome/free-solid-svg-icons'
import { faUser } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'

import '@/scss/_custom.scss'

library.add(faSearch)
library.add(faCamera)
library.add(faPlus)
library.add(faTrashAlt)
library.add(faEdit)
library.add(faUser)
Vue.component('font-awesome-icon', FontAwesomeIcon)

Vue.use(BootstrapVue)

Vue.config.productionTip = false


var keycloak = Keycloak({
    url: 'http://localhost:8080/auth',
    realm: 'apigw',
    clientId: 'web-sso'
});

Vue.prototype.$keycloak = keycloak;

Vue.prototype.$myStore = new Vue({
    data: {
       // token property returning the ls token value
       loggedIn: window.localStorage.getItem('status'),
       username: window.localStorage.getItem('username')
    },
    watch:{
       // watcher listening for changes on the token property
       // to ensure the new value is written back to ls
       loggedIn(value) {
         window.localStorage.setItem('status', value)
       },

       username(value) {
         window.localStorage.setItem('username', value)
       }
    }
})

new Vue({
  router,
  // keycloak,
  render: h => h(App)
}).$mount('#app')
