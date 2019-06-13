import Vue from 'vue'
import Router from 'vue-router'

// import store from 'plugin-vuejs-keycloak'
// import security from 'plugin-vuejs-keycloak/security'

import Results from '@/views/AdsResult.vue'

Vue.use(Router)

const router = new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      name: 'results',
      component: Results
    },
    {
      path: '/about',
      name: 'about',
      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import(/* webpackChunkName: "about" */ './views/About.vue')
    },
    {
      path: '/upload',
      name: 'upload',
      // meta: { abc: 'efg' },
      // meta: { requiresAuth: true, roles: ['user'] },
      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import(/* webpackChunkName: "about" */ './views/Upload.vue')
    },
    {
      path: '/login',
      name: 'login',
      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import(/* webpackChunkName: "about" */ './views/Login.vue')
    },
    {
      path: '/signup',
      name: 'signup',
      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import(/* webpackChunkName: "about" */ './views/Signup.vue')
    },
    {
      path: '/newad',
      name: 'newad',
      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      meta: { requiresAuth: true },
      component: () => import(/* webpackChunkName: "about" */ './views/NewAd.vue')
    },
    {
      path: '/ad/:id',
      name: 'detailedad',
      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      meta: { onlyRefreshToken: true },
      component: () => import(/* webpackChunkName: "about" */ './views/DetailedAd.vue'),
      props: true,

    },
    {
      path: '/categories/:id',
      name: 'category',
      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: Results,
      props: true,

    },
    // {
    //   path: '/correctly-logged-in',
    //   name: 'correctly-logged-in',
    //   // route level code-splitting
    //   // this generates a separate chunk (about.[hash].js) for this route
    //   // which is lazy-loaded when the route is visited.
    //   component: () => import(/* webpackChunkName: "about" */ './views/Correct.vue')
    // },
    {
      path: '/unauthorized',
      name: 'Unauthorized',
      component: () => import(/* webpackChunkName: "about" */ './views/About.vue')
    }, // Unauthorized
    {
      path: '/myads',
      name: 'myads',
      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      meta: { requiresAuth: true },
      component: () => import(/* webpackChunkName: "about" */ './views/MyAds.vue')
    },
  ]
})

router.beforeEach((to, from, next) => {
  // console.log('value of to');
  // console.log(to);
  // alert('About to change route')
  // alert('Before each route')
  if(to.meta.requiresAuth) {
    // console.log(Vue.prototype.$myStore.loggedIn);
    // alert('this page requires auth')

    if(Vue.prototype.$myStore.loggedIn === 'in') {

      Vue.prototype.$keycloak.init()
      Vue.prototype.$keycloak.updateToken(1770).success(function(refreshed) {
          if (refreshed) {
              console.log('Token was successfully refreshed');
              // console.log(Vue.prototype.$keycloak.token)
              Vue.prototype.$axios.defaults.headers.common['Authorization'] = 'Bearer ' +  Vue.prototype.$keycloak.token;
              // delete Vue.prototype.$axios.defaults.headers.common['Authorization']// = 'Bearer ' +  Vue.prototype.$keycloak.token;
          } else {
              console.log('Token is still valid');
          }
      }).error(function() {
          console.log('Failed to refresh the token, or the session has expired');
      });

      next()
    }
    else {

      Vue.prototype.$keycloak.init({ onLoad: 'login-required' }).success((auth) =>{

          localStorage.setItem("vue-token", Vue.prototype.$keycloak.token);
          // localStorage.setItem("status", 'in')
          Vue.prototype.$myStore.loggedIn = 'in'

          var userInfos = Vue.prototype.$keycloak.tokenParsed;
          console.log('User profile:');
          console.log(userInfos);
          Vue.prototype.$myStore.username = userInfos.preferred_username
          Vue.prototype.$myStore.userid = userInfos.sub
          Vue.prototype.$myStore.refreshToken = Vue.prototype.$keycloak.refreshToken;

          Vue.prototype.$axios.defaults.headers.common['Authorization'] = 'Bearer ' +  Vue.prototype.$keycloak.token;

          next()

      }).error(() =>{
        Vue.$log.error("Authenticated Failed");
        // console.log('Dans error')
      });

      next(false)
    }
  }


  else if(to.meta.onlyRefreshToken) {
    if(Vue.prototype.$myStore.loggedIn === 'in') {

      Vue.prototype.$keycloak.init()
      Vue.prototype.$keycloak.updateToken(1770).success(function(refreshed) {
          if (refreshed) {
              console.log('Token was successfully refreshed');
              // console.log(Vue.prototype.$keycloak.token)
              // delete Vue.prototype.$axios.defaults.headers.common['Authorization']
              Vue.prototype.$axios.defaults.headers.common['Authorization'] = 'Bearer ' + Vue.prototype.$keycloak.token;
          } else {
              console.log('Token is still valid');
          }
      }).error(function() {
          console.log('Failed to refresh the token, or the session has expired');
      });

      next()
    }
    else {
      next()
    }

  }
  else {
    console.log('log in before each');
    console.log(to.meta.requiresAuth);
    // alert('alert in before each')
    next()
  }
})

// router.beforeEach((to, from, next) => {
//   if (to.meta.requiresAuth) {
//     const auth = store.state.security.auth
//     if (!auth.authenticated) {
//       security.init(next, to.meta.roles)
//     }
//     else {
//       if (to.meta.roles) {
//         if (security.roles(to.meta.roles[0])) {
//           next()
//         }
//         else {
//           next({ name: 'unauthorized' })
//         }
//       }
//       else {
//         next()
//       }
//     }
//   }
//   else {
//     next()
//   }
// })
//
export default router
