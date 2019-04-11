<template>
  <div id="app">
    <navbar></navbar>
    <router-view></router-view>
    <foot></foot>

  </div>
</template>

<script>
  import Navbar from "@/components/Navbar";
  import Foot from "@/components/Foot";
  import Menu from "@/components/Menu";



  export default {
    name: "App",
    components: {
      'navbar' : Navbar,
      'foot' : Foot,
      'menut' : Menu
    }
  };


</script>
<style lang="scss">
// Import Bulma's core
@import "~bulma/sass/utilities/_all";
$navbar-height: 10rem;
$navbar-item-img-max-height: 7rem;
$navbar-background-color: #da4869;


// Import Bulma and Buefy styles
@import "~bulma";
@import "~buefy/src/scss/buefy";


</style>
<style>
</style>
