<!-- <template>
  <div class="logged-in">
    <b-modal id="modal-center" centered title="BootstrapVue" v-model="showModal" hide-footer hide-header>

      <h1>Connecté !</h1>
    </b-modal>
  </div>
</template>

<script>

export default {
  name: 'logged-in',
  data() {
    return {
      showModal: false,
    }
  },
  // mounted() {
  //   var here = window.location.href;
  //   var before = document.referrer;
  //   // console.log(here, before);
  //   if(here.indexOf('#state') != -1 && before.indexOf('auth/realms/apigw/protocol/openid-connect/') != -1) {
  //     // If we have the long url in the title and we come from the login page
  //     // ok keycloak, we want to refresh the page using this.$keycloak.init()
  //     // to retrieve the jwt token
  //     // console.log('Need to reload');
  //
  //     this.$keycloak.init({ onLoad: 'login-required' }).success((auth) =>{
  //
  //         if(!auth) {
  //           // window.location.reload();
  //           console.log('Not authenticated in successs');
  //         } else {
  //           // Vue.$log.info("Authenticated");
  //           console.log('Authenticated in success');
  //         }
  //
  //         localStorage.setItem("vue-token", this.$keycloak.token);
  //         localStorage.setItem("status", 'in')
  //         console.log('Token set successfully');
  //         // this.$myStorage.loggedIn = 'in'
  //
  //         console.log('Storage from cuess keycloak init');
  //         console.log(this.$myStore.loggedIn);
  //         this.$myStore.loggedIn = 'in'
  //
  //         // this.$router.push()
  //
  //     }).error(() =>{
  //       Vue.$log.error("Authenticated Failed");
  //       // console.log('Dans error')
  //     });
  //   }
  //   // else {
  //   //   console.log('No need to reload');
  //   // }
  // }
}

</script>

<style lang="scss" scoped>
// h1 {
//   font-size: 10em;
// }
</style> -->
