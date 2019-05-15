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
            <b-img v-bind:src="image" rounded width="100px" height="100px"></b-img>
          </div>
          <b-button class="new-photo" variant="outline-primary"> <font-awesome-icon style="font-size: 2em;" icon="camera"/> </b-button>
          <input type="file" class="invisible-file-input">
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
      categories: [
        { value: 1, text: 'Ordinateur' },
        { value: 2, text: 'Habits homme' },
        { value: 3, text: 'Habits femme' },
        { value: 4, text: 'Livres' },
      ],
      images: []//["https://picsum.photos/600/300/?image=23", "https://picsum.photos/600/300/?image=24", "https://picsum.photos/600/300/?image=25", "https://picsum.photos/600/300/?image=25", "https://picsum.photos/600/300/?image=25", "https://picsum.photos/600/300/?image=26"]
    }
  },
  methods: {
    submit: function (event) {
       // `this` inside methods points to the Vue instance
       // Data
       var data = {"title" : this.title,
                    "description": this.description,
                    "price": this.price,
                    "categoryID": this.categoryID,
                    "userID":0};


          axios
         .post('http://localhost:8081/classads',data)
         .then((response) => {
           // Success
           this.$router.push('/');
         })
         .catch(error => {
           alert('Request failed');
         });

     }


    // format(value, event) {
    //   console.log('[' + value + ']');
    //   console.log('Caller event: ' + event);
    //   if(!value.includes('.')) {
    //     console.log('No dot');
    //     return value;
    //   }
    //
    //   if((value.split(".").length) > 2) { // Si il y a plus d'une virgule
    //   console.log('More than one dot');
    //     var elements = value.split(".");
    //     console.log(elements);
    //     var toReturn = elements[0] + "." + elements[1]
    //     console.log("Value to return: " + toReturn);
    //     return toReturn;
    //   }
    //
    //   console.log('Exactly one dot');
    //   return value;
    // }
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
