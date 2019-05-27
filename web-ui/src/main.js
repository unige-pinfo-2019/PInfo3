import Vue from 'vue'
import VueImg from 'v-img';
import App from './App.vue'
import router from './router'
import BootstrapVue from 'bootstrap-vue'

import { library } from '@fortawesome/fontawesome-svg-core'
import { faSearch, faCamera, faPlus, faTrashAlt, faEdit, faStar as fasStar, faTimes } from '@fortawesome/free-solid-svg-icons'
library.add(faSearch, faCamera, faPlus, faTrashAlt, faEdit, fasStar, faTimes)

import { faStar as farStar} from '@fortawesome/free-regular-svg-icons'
library.add(farStar)

import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'

import '@/scss/_custom.scss'

Vue.component('font-awesome-icon', FontAwesomeIcon)

Vue.use(BootstrapVue)
Vue.use(VueImg);


Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
