<template>
  <div id="app">
    <Banner/>

    <!-- <b-row>
      <b-col cols="2">
        <LeftMenu/>
      </b-col>
      <b-col cols="10">
        <router-view class="test"/>
      </b-col>
    </b-row> -->

    <div class="center-wrapper">
      <div class="sidebar">
        <LeftMenu/>
      </div>
      <div class="router-wrapper">
        <router-view class="test"/>
      </div>
    </div>


    <OurFooter/>
  </div>
</template>

<script>
import Banner from '@/components/Banner.vue'
import MiniAd from '@/components/MinimizedAd.vue'
import LeftMenu from '@/components/LeftMenu.vue'
import OurFooter from '@/components/Footer.vue'

export default {
  name: 'app',
  components: {
    Banner,
    LeftMenu,
    OurFooter
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
  // overflow-x: hidden;
}

.test {
  min-height: 600px;
}

.center-wrapper {
  display: flex;
  // grid-template-columns: 20% 80%;
  // grid-column-gap: 30px;
}

.sidebar {
  width: 100%;
  text-align: left;
  // border: 1px solid black;
  flex-basis: 20%;
  margin-right: 30px;
}

.router-wrapper {
  width: 100%;
  text-align: center;
  // border: 1px solid black;
  padding-right: 30px;
}

</style>
