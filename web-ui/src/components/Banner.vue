<template>
  <div class="banner">
    <div class="banner-container">
      <img class="banner-img" src="../assets/triangles.png" alt="">
      <span class="brand-name-container"> <h1 class="brand-name">UniTrade</h1> </span>
      <Login class="login"/>
      <!-- <div class="wrap">
        <div class="center" style="background: #888; min-height: 50px; min-width: 100px; float: right;">
      </div> -->

      <!-- </div> -->
    </div>
  </div>
</template>

<script>
import Login from '@/components/Login.vue'

export default {
  name: 'banner',
  components: {
    Login
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style lang="scss" scoped>

// .wrap{
//     width: 500px;
//     overflow:hidden;
//     background: pink;
// }

// .center {
//   // float: right;
//   // display: inline-block;
//   // vertical-align: middle;
//   // margin: 0;
//   // // position: absolute;
//   //
//   // top: 50%;
//   // -ms-transform: translateY(-50%);
//   // transform: translateY(-50%);
// }

.login {
  // background-color: #888;
  float: right;
  margin-top: 30px;
  // margin: auto 0px;
}

.banner {
  object-fit: fill;
  object-position: 0% 0;

  height: 100px;
}

.banner-img {
  height: 178px;
  object-fit: cover;
  float: left;

  opacity: 0.2;
  filter: alpha(opacity=20); /* For IE8 and earlier */
}

.brand-name {
  color: white;
  position: absolute;
  top: 0px;
  left: 0px;
  padding-left: 50px;
  padding-top: 30px;
}

.banner-container {
  background: linear-gradient(0.25turn, $primary-color, $secondary-color);
  height: 100px;
  overflow-y: hidden;
  overflow-x: hidden;
  position: relative;
}

</style>
