<template>
  <div id="app">
    <Banner/>

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
import LeftMenu from '@/components/LeftMenu.vue'
import OurFooter from '@/components/OurFooter.vue'

export default {
  name: 'app',
  components: {
    Banner,
    LeftMenu,
    OurFooter
  },
  mounted() {
    var here = window.location.href;
    var before = document.referrer;

    if(( here.indexOf('#state') != -1 )) {
      // If we have the long url in the title and we come from the login page
      // ok keycloak, we want to refresh the page using this.$keycloak.init()
      // to retrieve the jwt token

      this.$keycloak.init({ onLoad: 'login-required' }).success((auth) =>{

          if(!auth) {
            // window.location.reload();
            console.log('Not authenticated in successs');
          } else {
            // Vue.$log.info("Authenticated");
            console.log('Authenticated in success');
          }

          localStorage.setItem("vue-token", this.$keycloak.token);
          // localStorage.setItem("status", 'in')
          this.$myStore.loggedIn = 'in'

          var userInfos = this.$keycloak.tokenParsed;
          // console.log('User profile:');
          // console.log(userInfos);
          this.$myStore.username = userInfos.preferred_username
          this.$myStore.userid = userInfos.sub
          this.$myStore.refreshToken = this.$keycloak.refreshToken;

          this.$axios.defaults.headers.common['Authorization'] = 'Bearer ' +  this.$keycloak.token;

      }).error(() =>{
        Vue.$log.error("Authenticated Failed");
        // console.log('Dans error')
      });
    }
  }
}
</script>

<style lang="scss">

@import url('https://fonts.googleapis.com/css?family=Lato');

#app {
  font-family: "Lato", Helvetica, Arial, sans-serif;
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
  min-height: 700px;
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
