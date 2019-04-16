<template>
  <div id="app">
    <!-- <div id="nav">
      <router-link to="/">Home</router-link> |
      <router-link to="/about">About</router-link>
    </div>
    <router-view /> -->
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

<style lang="scss">
// $primary-color: #002766;
$primary-color: #D80669;


.navbar-custom {
    background-color: $primary-color;
}

/* change the color of active or hovered links */
.navbar-custom .nav-item .nav-link {
    color: white;
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
