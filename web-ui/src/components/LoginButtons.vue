<template>
  <div class="minimized-ad">
    <div v-if="!($myStore.loggedIn === 'in')">
      <b-button v-on:click="askLogin()" id="in" class="login" variant="light">Se connecter</b-button>
      <b-button v-on:click="askRegister()" id="up" class="login" variant="outline-light">S'inscrire</b-button>
    </div>
    <div class="minimized-ad" v-else>
      <h4 class="login"> <font-awesome-icon class="icon" icon="user"/> {{$myStore.username}}</h4>
      <b-button to="/myads" id="myads" class="login" variant="outline-light">Mes annonces</b-button>
      <!-- <b-button v-on:click="editProfile()" id="edit" class="login" variant="outline-light">Éditer son profil</b-button> -->
      <b-button v-on:click="disconect()" id="deco" class="login" onloadedmetadata="" variant="outline-light">Déconnexion</b-button>
    </div>

  </div>
</template>

<script>

export default {
  name: 'login',
  data() {
    return {
      isLoggedIn: false
    }
  },
  mounted() {
    // console.log('My store:');
    // console.log(this.$myStore.loggedIn);

    // console.log(localStorage.getItem('status'));
    // if(localStorage.getItem('status') === 'in') {
    //   this.loggedIn = true
    //   console.log('normalement connecté');
    // }
    // else {
    //   this.loggedIn = false;
    //   console.log('normalement PAS connecté');
    // }
  },
  methods: {
    askLogin() {

      this.$keycloak.init({ onLoad: 'login-required' }).success((auth) => {

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


      // console.log(this.$store._actions.login[0]());
      // this.$store._actions.login[0]()
      // this.$store.dispatch('login')

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
      // this.$store.dispatch('register')
      this.$keycloak.init()
      this.$keycloak.register()
    },
    disconect() {
      // this.$store._actions.logout[0]()
      this.$keycloak.init()
      this.$keycloak.logout()
      console.log('Disconected !');
      localStorage.removeItem('vue-token')
      // localStorage.setItem('status', 'out')


      this.$myStore.loggedIn = 'out'
      localStorage.removeItem('username')
      localStorage.removeItem('userid')
      localStorage.removeItem('refresh-token')

      delete this.$axios.defaults.headers.common['Authorization'];
    },
    editProfile() {
      this.$keycloak.loadUserProfile()
    }
  },
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style lang="scss" scoped>

.icon {
  margin-right: 6px;
}

h4 {
  margin-bottom: 0;
  color: white;
  font-size: 1.2em;
}

.minimized-ad {
  display: flex;
  flex-direction: row;
  align-items: center;
}

.login {
  margin-right: 20px;
}

#in {
  color: #ea5a5f;
}

#up:hover, #deco:hover, #edit:hover, #myads:hover {
  color: #ea5a5f;
}

</style>
