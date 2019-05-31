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
      myToken: null,
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
    }
  },
  mounted: function() {
    this.baseAPI = process.env.VUE_APP_BASE_API;

    // this.myToken = keycloak.token;
    this.myToken = localStorage.getItem('vue-token');

    console.log('Auth status: ');
    console.log(this.$store.getters.authStatus);
    this.myAuthStatus = this.$store.getters.authStatus
  }
}
</script>

<style lang="scss" scoped>

</style>
