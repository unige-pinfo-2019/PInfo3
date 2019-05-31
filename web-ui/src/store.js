import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'
import Keycloak from 'keycloak-js'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    status: '',
    token: localStorage.getItem('token') || '',
    user : {},
    keycloak: null,
  },
  mutations: {
    auth_request(state){
      state.status = 'loading'
    },
    auth_success(state, token, user){
      state.status = 'success'
      state.token = token
      state.user = user
    },
    auth_error(state){
      state.status = 'error'
    },
    logout(state){
      state.status = ''
      state.token = ''
    },
    // loginTest() {
    //   state.keycloak = state.keycloak.init()
    // }
  },
  actions: {
    login({commit}){
      return new Promise((resolve, reject) => {
        // commit('auth_request')

        console.log('Tryin to loggin');

        state.keycloak.init({ onLoad: 'login-required' }).success((auth) =>{
          // console.log('Response received:');
          // console.log(auth);
          // const token = keycloak.token
          // const user = 'not set'
          // // const user = keycloak.userid
          // console.log('Authenticated !');
          // localStorage.setItem('token', token)
          // axios.defaults.headers.common['Authorization'] = token
          // commit('auth_success', token, user)
          // resolve(resp)
              if(!auth) {
                // window.location.reload();
                console.log('Not authenticated');
              } else {
                // Vue.$log.info("Authenticated");
                console.log('Authenticated');
              }

              localStorage.setItem("vue-token", keycloak.token);
        })
        .error(err => {
          commit('auth_error')
          localStorage.removeItem('token')
          reject(err)
        })
      })
    },


        // keycloak.onAuthSuccess = function() {
        //   const token = keycloak.token;
        //   localStorage.setItem('token', token)
        // }
        // keycloak.init()
        // // keycloak.onAuthSuccess = function() {
        // //   alert('Autenticated yes :D');
        // //   console.log("Also autenticated !");
        // // }
        // keycloak.login()


        // keycloak.login().success((auth) =>{
        //   const token = keycloak.token
        //   const user = keycloak.username
        //   console.log('Authenticated !');
        //   localStorage.setItem('token', token)
        //   axios.defaults.headers.common['Authorization'] = token
        //   commit('auth_success', token, user)
        //   resolve(resp)
        // })
        // .catch(err => {
        //   commit('auth_error')
        //   localStorage.removeItem('token')
        //   reject(err)
        // })

        // keycloak.init({ onLoad: 'login-required' }).success((auth) =>{
        //   const token = keycloak.token
        //   const user = keycloak.username
        //   localStorage.setItem('token', token)
        //   axios.defaults.headers.common['Authorization'] = token
        //   commit('auth_success', token, user)
        //   resolve(resp)
        // })
        // .catch(err => {
        //   commit('auth_error')
        //   localStorage.removeItem('token')
        //   reject(err)
        // })
      // })
    // },
    register({commit}, user){
      return new Promise((resolve, reject) => {
        commit('auth_request')

        var keycloak = Keycloak({
          url: 'http://localhost:8080/auth',
          realm: 'apigw',
          clientId: 'web-sso'
        });
        keycloak.init()
        keycloak.register()

        // axios({url: 'http://localhost:3000/register', data: user, method: 'POST' })
        // .success(resp => {
        //   const token = resp.data.token
        //   const user = resp.data.user
        //   localStorage.setItem('token', token)
        //   axios.defaults.headers.common['Authorization'] = token
        //   commit('auth_success', token, user)
        //   resolve(resp)
        // })
        // .error(err => {
        //   commit('auth_error', err)
        //   localStorage.removeItem('token')
        //   reject(err)
        // })
      })
    },
    logout({commit}){
      return new Promise((resolve, reject) => {
        commit('logout')

        var keycloak = Keycloak({
          url: 'http://localhost:8080/auth',
          realm: 'apigw',
          clientId: 'web-sso'
        });
        keycloak.init()
        keycloak.logout()

        console.log('Disconected ! :D');

        localStorage.removeItem('token')
        localStorage.removeItem('vue-token')
        delete axios.defaults.headers.common['Authorization']
        resolve()
      })
    }
  },
  getters : {
    isLoggedIn: state => !!state.token,
    authStatus: state => state.status,
  },
})
