<template>
  <div class="UploadForm">
    <input type="file" @change="onFileChanged">
    <br>
    <button @click="onUpload">Upload!</button>
    <br>
    Lien de l'image :     {{ info }}

    <br>
    <hr>

    BASE_API 2: [{{baseAPI}}]

    <hr>

    <input type="button" name="" value="Show token" v-on:click="refreshTokenDisplay">
    {{myToken}}

    <hr>

    <input type="button" v-on:click="disconect()"  value="Deconnexion">

    <hr>

    Status: {{myAuthStatus}}

  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'UploadForm',
  data() {
    return {
      selectedFile: null,
      info:null,
      baseAPI: "not set",
      myToken: 'not set yet',
      myAuthStatus: null
    }
  },

  methods: {
    onFileChanged (event) {
      this.selectedFile = event.target.files[0]
      console.log(this.selectedFile)

    },
    onUpload() {
      // Authentification
      var config = {
        headers: {'Authorization': 'Client-ID a98be453a893668'}
      };
      // Data
      var data = new FormData();
      data.append("image", this.selectedFile);

      axios
        .post('https://api.imgur.com/3/image',data, config)
        .then(response => (this.info = response.data.data.link))
    },
    disconect() {
      this.$store._actions.logout[0]()
      this.$myStore.loggedIn = 'out'
    },
    refreshTokenDisplay() {
      this.myToken = localStorage.getItem('vue-token');
    }
  },
  mounted: function() {
    this.baseAPI = process.env.VUE_APP_BASE_API;

    // var here = window.location.href;
    // var before = document.referrer;
    // if(here.indexOf('#state') != -1 || before.indexOf('auth/realms/apigw/protocol/openid-connect/auth?') != -1) {
    //   // If we have the long url in the title and we come from the login page
    //   // ok keycloak, we want to refresh the page using this.$keycloak.init()
    //   // to retrieve the jwt token
    //
    //   this.$keycloak.init({ onLoad: 'login-required' }).success((auth) =>{
    //
    //       if(!auth) {
    //         // window.location.reload();
    //         console.log('Not authenticated in successs');
    //       } else {
    //         // Vue.$log.info("Authenticated");
    //         console.log('Authenticated in success');
    //       }
    //
    //       localStorage.setItem("vue-token", this.$keycloak.token);
    //       this.myToken = localStorage.getItem('vue-token');
    //
    //   }).error(() =>{
    //     Vue.$log.error("Authenticated Failed");
    //     console.log('Dans error')
    //   });
    // }

    this.myToken = localStorage.getItem('vue-token');

    ///////////////////////////////////////////
    // console.log('Trying to retrieve the token');
    // console.log(this.myToken);
    // console.log('becomes');
    // this.myToken = this.$keycloak.token//localStorage.getItem('vue-token');
    // console.log(this.myToken);
    //
    // console.log(this.$keycloak);
    ///////////////////////////////////////////

    // console.log('Auth status: ');
    // console.log(this.$store.getters.authStatus);
    // this.myAuthStatus = this.$store.getters.authStatus
  }
}
</script>

<style lang="scss" scoped>

</style>
