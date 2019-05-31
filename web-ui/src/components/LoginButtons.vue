<template>
  <div class="minimized-ad">
    <b-button v-on:click="askLogin()" id="in" class="login" variant="light">Se connecter</b-button>
    <b-button v-on:click="askRegister()" id="up" class="login" variant="outline-light">S'inscrire</b-button>
  </div>
</template>

<script>
import Keycloak from 'keycloak-js'

export default {
  name: 'login',
  methods: {
    askLogin() {
      // var keycloak = Keycloak({
      //     url: 'http://localhost:8080/auth',
      //     realm: 'apigw',
      //     clientId: 'web-sso'
      // });
      // alert(keycloak.);
      alert(localStorage.getItem("vue-token"))
      this.$keycloak.init({ onLoad: 'login-required' }).success((auth) =>{

          if(!auth) {
            window.location.reload();
            console.log('Not authenticated');
          } else {
            // Vue.$log.info("Authenticated");
            console.log('Authenticated');
              alert('Authenticated : ' +  keycloak.token)
          }

          alert('Dans success')
          localStorage.setItem("vue-token", keycloak.token);

      }).error(() =>{
        Vue.$log.error("Authenticated Failed");
        alert('Dans error')
      });
      // console.log(this.$store._actions.login[0]());
      // this.$store._actions.login[0]()
      // this.$store.dispatch('login')

      // var keycloak = Keycloak({
      //     url: 'http://localhost:8080/auth',
      //     realm: 'apigw',
      //     clientId: 'web-sso'
      // });
      //
      // keycloak.init({ onLoad: 'login-required' }).success((auth) =>{
      //
      //     if(!auth) {
      //       // window.location.reload();
      //       console.log('Not authenticated');
      //     } else {
      //       // Vue.$log.info("Authenticated");
      //       console.log('Authenticated');
      //     }
      //
      //     localStorage.setItem("vue-token", keycloak.token);
      //     // localStorage.setItem("vue-refresh-token", keycloak.refreshToken);
      //
      //     // setTimeout(() =>{
      //     //   keycloak.updateToken(70).success((refreshed)=>{
      //     //     if (refreshed) {
      //     //       Vue.$log.debug('Token refreshed'+ refreshed);
      //     //     } else {
      //     //       Vue.$log.warn('Token not refreshed, valid for '
      //     //       + Math.round(keycloak.tokenParsed.exp + keycloak.timeSkew - new Date().getTime() / 1000) + ' seconds');
      //     //     }
      //     //   }).error(()=>{
      //     //       Vue.$log.error('Failed to refresh token');
      //     //   });
      //     //
      //     //
      //     // }, 60000)
      //
      // }).error(() =>{
      //   Vue.$log.error("Authenticated Failed");
      // });
    },
    askRegister() {
      // this.$store._actions.register[0]()
      this.$store.dispatch('register')
    }
  },
  // mounted() {
  //
  // }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style lang="scss" scoped>
.login {
  margin-right: 20px;
}

#in {
  color: #ea5a5f;
}

#up:hover {
  color: #ea5a5f;
}

</style>
