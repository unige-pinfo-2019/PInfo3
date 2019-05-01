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
      <b-col cols="2">
        <LeftMenu/>
      </b-col>
      <b-col cols="10">
        <router-view class="test"/>
      </b-col>
    </b-row>

    <Footer/>
  </div>
</template>

<script>
import Banner from '@/components/Banner.vue'
import MiniAd from '@/components/MinimizedAd.vue'
import LeftMenu from '@/components/LeftMenu.vue'
import Footer from '@/components/Footer.vue'

export default {
  name: 'app',
  components: {
    Banner,
    LeftMenu,
    Footer
  }
}
</script>

<style lang="scss">

#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  // color: #2c3e50;
  // color: $primary-color;
  // background-color: #e6ecf0;
  background-color: $background-gray;
  overflow-x: hidden;
}

.test {
  min-height: 600px;
}

</style>
