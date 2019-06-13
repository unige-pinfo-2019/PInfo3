<template>
  <div class="edit">
    <div class="container">
      <div class="block">

        <span class="field-title" > <h4>Titre</h4> <hr> </span>
        <div class="input">
          <b-form-input v-model="title"
          id="input-1"
          type="email"
          :state="titleState"
          required
          ></b-form-input>

          <b-form-invalid-feedback id="input-live-feedback">
            Veuillez entrer un titre
          </b-form-invalid-feedback>
        </div>

        <span class="field-title" > <h4>Description</h4> <hr> </span>

        <div class="input">
          <b-form-textarea v-model="description"
            id="textarea-auto-height"
            :state="descriptionState"
            rows="6"
            max-rows="12"
            required
          ></b-form-textarea>
          <b-form-invalid-feedback id="input-live-feedback">
            Veuillez entrer une description
          </b-form-invalid-feedback>
        </div>

        <span class="field-title" > <h4>Images</h4> <hr> </span>


        <div class="images-wrapper">
          <div class="image-wrapper" v-for="(image, index) in images" v-bind:key="image.id">

            <b-img v-bind:src="image" rounded width="100px" height="100px" v-img></b-img>

            <div class="buttons-container">
              <!-- <b-link class="delete-img">X</b-link> -->
              <div class="star-container">
                <a v-on:click="starThisPicture(index)">
                  <font-awesome-icon v-if="staredImage == index" style="color: #FFD700;" :icon="['fas', 'star']"/>
                  <font-awesome-icon v-else style="color: #FFD700;" :icon="['far', 'star']"/>
                </a>
              </div>

              <div class="delete-container">
                <a v-on:click="deleteThisPicture(index)">
                  <font-awesome-icon style="color: red; transform: scale(1.2) translateX(-2px);" icon="times"/>
                </a>
              </div>
            </div>


          </div>
          <b-button onclick="document.getElementById('hidden-file-input').click()" class="new-photo" variant="outline-primary"> <font-awesome-icon style="font-size: 2em;" icon="camera"/> </b-button>


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
            min = "0"
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


    <b-modal id="sending-notification" hide-header hide-footer centered v-model="sending">
      <div style="font-size: 1.7em;">
        Envoi en cours <b-spinner style="margin-left: 20px;" label="Small Spinner" variant="primary"></b-spinner>
      </div>
    </b-modal>
    <b-modal id="sending-notification-2" ok-only hide-header centered v-model="validation">
      <div style="font-size: 1.7em;">
        Veuillez remplir les champs nécessaires.
      </div>
    </b-modal>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'edit',
  props: {
    id: String,
    required:false,
    default:"1"
  },
  data() {
    return {
      data: null,
      title: '',
      description: '',
      validation : false,
      errorTitle: 'Hello World',
      price: 0.0, // float
      categoryID: 0,
      selectedFile: null,
      selectedFiles : [],
      srcurl:null,
      sending: false,
      categories: [
        { value: 1, text: 'Vide' },
      ],
      images: [],
      staredImage: 0
    }
  },
  computed: {
      titleState() {
        if (this.title.length > 0) {
          return true;
        }
        else
        {
          return false;
        }
      },
      descriptionState() {
        if (this.description.length > 0) {
          return true;
        }
        else
        {
          return false;
        }
      }
    },
  created: function() {
    this.fetchData();
  },
  mounted:  function () {
    // retrieve categories
    function getValues() {
      return axios
      .get(process.env.VUE_APP_BASE_API + ':8081/categories/index')
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
    starThisPicture(index) {
      this.staredImage = index;
    },
    async fetchData() {
      this.data = await axios.get(process.env.VUE_APP_BASE_API + ':8081/classads/ads/ad/' + this.id);
      this.title = this.data.data.title;
      this.description = this.data.data.description;
      this.images = this.data.data.images;
      this.price = this.data.data.price;
      this.categoryID = this.data.data.categoryID;
      this.images = this.data.data.images;


    },
    deleteThisPicture(index) {
      if(this.staredImage == index) {
        this.staredImage = 0;
      }

      this.images.splice(index, 1);
      this.selectedFiles.splice(index, 1);
    },
    onFileChanged (event) {
      this.selectedFile = event.target.files[0];
      this.selectedFiles.push(event.target.files[0]);
      var src = window.URL.createObjectURL(this.selectedFile);
      this.images.push(src);
    },
    submit: function (event) {
       // `this` inside methods points to the Vue instance
       // On upload l'image sur imgur
       // Authentification
       if (this.title.length == 0 || this.description.length == 0) { this.validation = true;}
       else
       {
       var config = {
         headers: {'Authorization': 'Client-ID a98be453a893668'}
       };
       var self = this;
       // build the promises
       var promises = [];
       for (var i = 0; i < this.selectedFiles.length; i++) {
         var tmp = new FormData();
         tmp.append('image',this.selectedFiles[i]);
         var prom = this.$axios.post('https://api.imgur.com/3/image',tmp, config);
         promises.push(prom);
       }
       // execute the requests
       axios.all(promises)
       .then(axios.spread((...args) => {
         var images = [];
         for (let i = 0; i < args.length; i++) {
            console.log(args[i].data.data.link);
            images.push(args[i].data.data.link);
        }
        this.images.concat(images);
        // upload ad
        var data = {
                    "id" : this.id,
                    "deleted" : false,
                    "nbVues" : 0,
                    "time" : "2019-05-21T14:40:10.600755",
                    "title" : this.title,
                     "description": this.description,
                     "price": this.price,
                     "categoryID": this.categoryID,
                     "userID":0,
                     "images": this.images};
         this.$axios
        .put(process.env.VUE_APP_BASE_API + ':8081/classads/' ,data)
        .then(function (response) {
          self.$router.push('/');
        });

      }))
      .catch(error => {
        alert("Ad has failed to upload, please try again");
      });

       this.sending = true;
     }
   }
   }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style lang="scss" scoped>

a:hover {
  cursor: pointer;
}

.buttons-container {
  // background: #CCC;
  display: flex;
  flex-direction: row;
  justify-content: space-between;

  position: relative;
  top: -100px;
  padding: 2px 8px;
}

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
