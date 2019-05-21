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
    }
  },
  mounted: function() {
    this.baseAPI = process.env.VUE_APP_BASE_API;
  }
}
</script>

<style lang="scss" scoped>

</style>
