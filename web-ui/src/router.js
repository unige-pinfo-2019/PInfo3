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
      component: () => import(/* webpackChunkName: "about" */ './views/NewAd.vue')
    },
    {
      path: '/ad/:id',
      name: 'detailedad',
      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
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
  ]
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
