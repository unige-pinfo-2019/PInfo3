<template>
  <div id="app">
    <Banner/>

    <!-- <b-navbar class="navbar navbar-custom" type="dark">
      <b-navbar-brand href="#">UniTrade</b-navbar-brand>
      <b-nav-item to="/">Home</b-nav-item>
      <b-nav-item to="/about">About</b-nav-item>

      <b-navbar-nav class="ml-auto">
        <b-nav-item href="#">Login</b-nav-item>
        <b-nav-item href="#">Register</b-nav-item>
      </b-navbar-nav>
    </b-navbar> -->

    <b-row>
      <b-col>
        <b-button variant="primary" href="#">Poster un annonce</b-button>
        <b-list-group>
          <!-- <b-list-group-item button active>Poster une annonce</b-list-group-item> -->
          <b-list-group-item button="true" variant="outline-primary">Awesome link</b-list-group-item>
          <b-list-group-item button>Link with active state</b-list-group-item>
          <b-list-group-item button>Action links are easy</b-list-group-item>
          <b-list-group-item button>Disabled link</b-list-group-item>
        </b-list-group>
      </b-col>
      <b-col cols="10">
        <!-- 2 of 3 (wider) -->
        <!-- <router-view /> -->
        <MiniAd/>
      </b-col>
    </b-row>
  </div>
</template>

<script>
import Banner from './components/Banner.vue'
import MiniAd from './components/MinimizedAd.vue'

export default {
  name: 'app',
  components: {
    Banner,
    MiniAd
  }
}
</script>

<style lang="scss">

// .navbar-custom {
//     background-color: $dark-color;
//     // box-shadow: 0px 2px 5px grey;
// }
//
// /* change the color of active or hovered links */
// .navbar-custom .nav-item .nav-link {
//     color: $primary-color;
// }
//
// li {
//   list-style-type: none;
// }

#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  // color: #2c3e50;
  // color: $primary-color;
  // background-color: #e6ecf0;
  background-color: $background-gray;
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
