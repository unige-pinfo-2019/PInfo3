<template>
  <div id="app">
    <!-- <div class="banner-container">
      <img class="banner-img" src="./assets/trangles.png" alt="">
      <span class="brand-name-container"> <h1 class="brand-name">UniTrade</h1> </span>

    </div> -->
    <Banner/>


    <b-navbar class="navbar navbar-custom" type="dark">
      <b-navbar-brand href="#">UniTrade</b-navbar-brand>
      <b-nav-item to="/">Home</b-nav-item>
      <b-nav-item to="/about">About</b-nav-item>

      <!-- Right aligned nav items -->
      <b-navbar-nav class="ml-auto">
        <b-nav-item href="#">Login</b-nav-item>
        <b-nav-item href="#">Register</b-nav-item>
      </b-navbar-nav>
    </b-navbar>

    <router-view />
  </div>
</template>

<script>
import Banner from './components/Banner.vue'

export default {
  name: 'app',
  components: {
    Banner
  }
}
</script>

<style lang="scss">

.navbar-custom {
    background-color: $dark-color;
}

/* change the color of active or hovered links */
.navbar-custom .nav-item .nav-link {
    color: $primary-color;
}

li {
  list-style-type: none;
}

#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  // color: #2c3e50;
  color: $primary-color;
}

// #nav {
//   padding: 30px;
// }
//
// #nav a {
//   font-weight: bold;
//   color: #2c3e50;
// }
//
// #nav a.router-link-exact-active {
//   color: #42b983;
// }
</style>
