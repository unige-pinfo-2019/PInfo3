<template>
  <div class="new-ad">
    <div class="container">
      <div class="block">

        <span class="field-title" > <h4>Titre</h4> <hr> </span>
        <div class="input">
          <b-form-input v-model="title"
          id="input-1"
          type="email"
          required
          placeholder="Entrez un titre"
          ></b-form-input>
        </div>

        <span class="field-title" > <h4>Description</h4> <hr> </span>

        <div class="input">
          <b-form-textarea v-model="description"
            id="textarea-auto-height"
            rows="6"
            max-rows="12"
            required
            placeholder="Entrez une description"
          ></b-form-textarea>
        </div>

        <span class="field-title" > <h4>Images</h4> <hr> </span>

        <!-- <div class="custom-file">
          <input type="file" class="custom-file-input" id="customFile">
          <label class="custom-file-label" for="customFile">Choose file</label>
        </div> -->
        <div class="images-wrapper">
          <div class="image-wrapper" v-for="image in images" v-bind:key="image.id">
            <b-img v-bind:src="image" rounded width="100px" height="100px" v-img></b-img>
          </div>
          <b-button onclick="document.getElementById('hidden-file-input').click()" class="new-photo" variant="outline-primary"> <font-awesome-icon style="font-size: 2em;" icon="camera"/> </b-button>

          <b-modal ref="modalim" title="Aperçu" :ok-only='true'>
            <b-img class="img-responsive" v-bind:src="srcurl"></b-img>
          </b-modal>

          <!-- Cet input dessous est cliqué automatiquement lorsque le "vrai"
          bouton visible est cliqué. -->
          <input id="hidden-file-input" type="file" class="invisible-file-input" @change="onFileChanged">
        </div>



        <span class="field-title" > <h4>Prix</h4> <hr> </span>

        <b-input-group append="CHF" style="width: 45%;">
          <b-form-input
            id="price-field"
            v-model="price"
            required
            type="number"
            placeholder="0.00"

          ></b-form-input>
        </b-input-group>



      </div>

      <div class="block">

        <span class="field-title" > <h4>Catégories</h4> <hr> </span>

        <div class="input">
          <b-form-select v-model="categoryID" :options="categories" />
        </div>

      </div>

      <div class="block submit-flex">
        <b-button style="float: right;"  v-on:click="submit" variant="primary">Soumettre</b-button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'new-ad',
  data() {
    return {
      title: '',
      description: '',
      price: 0.0, // float
      categoryID: 0,
      selectedFile: null,
      srcurl:null,
      categories: [
        { value: 1, text: 'Vide' },
      ],
      images: []//["https://picsum.photos/600/300/?image=23", "https://picsum.photos/600/300/?image=24", "https://picsum.photos/600/300/?image=25", "https://picsum.photos/600/300/?image=25", "https://picsum.photos/600/300/?image=25", "https://picsum.photos/600/300/?image=26"]
    }
  },
  mounted: function () {
    // retrieve categories
    function getValues() {
      return axios
      .get('http://localhost:8081/categories/index')
      .then(response => {
        return response.data;
      })
    }
    getValues().then(data => {
      var listOfKeys = Object.keys(data);
      var formatedData = [];
      listOfKeys.forEach(function(elem) {
        var line = {value: null, text: null}
        line.text = elem;
        line.value = data[elem];
        formatedData.push(line);
      });
      this.categories = formatedData;
    })
  },
  methods: {
    onFileChanged (event) {
      this.selectedFile = event.target.files[0]
      var src = window.URL.createObjectURL(this.selectedFile);
      this.images.push(src);
      console.log(this.images);

    },
    showModal(image) {
      this.srcurl = image;
      this.$refs['modalim'].show()
    },

    submit: function (event) {
       // `this` inside methods points to the Vue instance
       // On upload l'image sur imgur
       // Authentification
       var config = {
         headers: {'Authorization': 'Client-ID a98be453a893668'}
       };
       // Data
       var data = new FormData();
       data.append("image", this.selectedFile);
       var dataad = {"title" : this.title,
                    "description": this.description,
                    "price": this.price,
                    "categoryID": this.categoryID,
                    "userID":0,
                    "images": []};
       axios
         .post('https://api.imgur.com/3/image',data, config)
         .then(function (response) {
           var imglink = response.data.data.link; // setup image link
           dataad["images"]=[imglink]
           // upload ad
              axios
             .post('http://localhost:8081/classads',dataad)
             .then(function (response) {
               // Success
               this.$router.push('/');
             })
         })
       .catch(error => {
         alert("Ad has failed to upload, please try again");
       });

  }
}
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style lang="scss" scoped>

.invisible-file-input {
  display: none;
}

.images-wrapper {
  // display: inline-block;
  // float: left;
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  // justify-content: flex-start;
  // align-items: flex-start;
  text-align: left;

}

.image-wrapper {
  margin-right: 20px;
  margin-bottom: 20px;
  // width: 50%;
}

.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 50px;
}

.block {
  width: 90%;
  max-width: 800px;
  background: white;
  min-height: 100px;
  border-radius: 3px;
  border: 1px solid lightgrey;
  padding: 20px 20px 20px 20px;
  margin-bottom: 30px;

  display: flex;
  flex-direction: column;
}

h4 {
  font-size: 1.3em;
  margin-bottom: 0px;
  font-weight: normal;
  // font-family: 'Open Sans', sans-serif;
}

.field-title > hr {
  border: 1px solid $primary-color;
  margin: 0px 0px 5px 0px;
  width: 100%;
}

.field-title {
  // transform: translateX(-50px);

  align-self: flex-start;

  // color: white;
  // background-color: $primary-color;
  // padding: 3px 10px 5px 10px;
  margin-bottom: 12px;

  // box-shadow: 5px 5px 5px #AAA;

  // width: 45%;
  text-align: left;
}

.input {
  margin-bottom: 40px;
}

.new-photo {
  // float: left;
  width: 100px;
  height: 100px;
  margin-bottom: 20px;
}

.submit-flex {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: flex-end;
}

#price-field {
  width: 45%;
  align-self: flex-start;
  height: 40px;
  font-size: 1.1em;
}

</style>
