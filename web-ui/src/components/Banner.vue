<template>
  <div class="banner">
    <!-- <p>ON AFFICHE BIEN LA BANIIERE</p> -->
    <div class="banner-container">
      <img class="banner-img" src="../assets/trangles.png" alt="">
      <span class="brand-name-container"> <h1 class="brand-name">UniTrade</h1> </span>

    </div>
  </div>
</template>

<script>
export default {
  name: 'banner'
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style lang="scss" scoped>
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
}

</style>
