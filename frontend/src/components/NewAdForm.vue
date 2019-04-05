<template>
  <div class="addContainer">

    <div class="addFields" >
      <h1>Ajouter une nouvelle annonce</h1>
      <h2>Titre</h2>
      <input type="text" placeholder="Entrez un titre" autofocus name="classAdTitle" v-model="adTitle">
      <br>
      <h2>Description</h2>
      <textarea rows = "5" placeholder="Entrez une description" name="classAdDescription" v-model="adDescr">
      </textarea>
      <div class="priceWrapper">

        <h2 class="priceRelated">Prix</h2>
        <input type="text" class="priceRelated" name="classAdPrice" placeholder="Entrez un prix" v-model="adPrice">

        <button type="button" v-on:click="addClassAd" class='important-button formButton' name="confirm">Poster l'annonce</button>
        <button type="button" class='formButton' name="cancel">Annuler</button>


      </div>
    </div>

    <div class="foundAd">
      <button type="button" class="foundAd-button" v-on:click="refreshAd">Refresh Ads</button>
    </div>
    <div class="ads-wrapper">
      <div v-if="foundAd && foundAd.length">
        <div class="ads-container" v-for="ad of foundAd">
          <button type="button" class="important-button delete-button" v-on:click="deleteAd(ad.id)">✕</button>
          <p style="font-weight: bold;">{{ad.titre}}</p>
          <p>{{ad.description}}</p>
          <p>Prix: {{ad.prix}} CHF</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: 'app',
  data() {
    return {
      adTitle: '',
      adDescr: '',
      adPrice: '',
      foundAd: []
    }
  },
//   mounted() {

//     // axios({ method: "GET", "url": "http://localhost:8080/hello" }).then(result => {
//     //     this.adTitle = result.data.origin;
//     //     console.log(result.data.origin);
//     // }, error => {
//     //     console.error(error);
//     // });
//   },
  methods: {
    addClassAd() { // Method appellée quand on appuie sur poster l'annonce
      // this.$http.get("http://localhost:8080/ClassAdd/new" + "/" + this.adTitle + "/" + this.adDescr + "/" + this.adPrice).then(result => {
      //     console.log("Message received ")
      //     this.adDescr = result.data;
      // }, error => {
      //     console.log("error : ")
      //     console.log(error);
      // });
      // alert(this.adTitle + " / " + this.adDescr + " / " + this.adPrice)
      axios.post('http://localhost:8080/ClassAd/new', {
        titre: this.adTitle,
        description: this.adDescr,
        prix: this.adPrice
      }).then(result => {
        console.log("Message sent")
        console.log(result.data)
      }, error => {
        console.log("error message not sent: ")
        console.log(error);
      });
    },
    refreshAd() {
      axios.get('http://localhost:8080/ClassAd').then( response => {
        this.foundAd = response.data
      }, error => {
        console.log("error result not found: ")
        console.log(error);
      });
    },
    deleteAd(id) {
      axios.delete('http://localhost:8080/ClassAd/delete?id=' + id).then( response => {
        console.log(response.data);
      }, error => {
        console.log("error result not found: ");
        console.log(error);
      });
      setTimeout(this.refreshAd, 100);
      // this.refreshAd();
    }
  }
}
</script>

<style>
/* #app {
  text-align: center;
  color: #2c3e50;
} */

html, body {
    /* height:100%; */
    /* overflow: hidden; */
}

.foundAd {
  margin-top: 90px;
  /* text-align: center; */
}

.foundAd-button {
  height: 40px;
  margin-bottom: 20px;
}


.ads-wrapper {
  display: block;
  width: 100%;
}

.delete-button {
  float: right;
  /* background-color: #d80669;
  color: white;
  border: 1px solid #82003d;
  border-radius: 3px; */
  width: 30px;
  height: 30px;
  margin: 8px 8px 0px 0px;
}

.ads-container {
  background: #f5f5f5;
  margin: 10px 0px;
  padding: 15px 15px;
  border: 1px solid #CCC;

  -webkit-box-sizing: border-box; /* Safari/Chrome, other WebKit */
  -moz-box-sizing: border-box;    /* Firefox, other Gecko */
  box-sizing: border-box;         /* Opera/IE*/
  width: 100%;
  /* border-radius: 3px; */
}

.addContainer {
  display: block;
  justify-content: center;
  align-items: center;

  width: 100%;
  /* height: 100vh; */
}

.addFields {
  /* width: 100%; /*Makes the form take all the possible width */ */
  -webkit-box-sizing: border-box; /* Safari/Chrome, other WebKit */
  -moz-box-sizing: border-box;    /* Firefox, other Gecko */
  box-sizing: border-box;         /* Opera/IE*/
  width: 100%;

  background-color: #f5f5f5;
  border-radius: 5px;
  border: 1px solid #CCC;
  padding: 20px;
}

h1 {
  text-align: center;
}

h2 {
  text-align: left;
  padding-top: 7px;
}

input, textarea {
  /*width: -moz-min-content;
  width: min-content; */
  -webkit-box-sizing: border-box; /* Safari/Chrome, other WebKit */
  -moz-box-sizing: border-box;    /* Firefox, other Gecko */
  box-sizing: border-box;         /* Opera/IE*/
  width: 100%;
}

input {
  height: 30px;
  background-color: white;
  border-radius: 3px;
  border: 1px solid #AAA;
  padding-left: 8px;
}

textarea:focus, input:focus {
  border: 1px solid #d80669;
}

/* [name="classAdTitle"] {
  height: 30px;
} */

[name="classAdDescription"] {
  height: 40vh;
  font-size: 14px;
  border: 1px solid #AAA;
  border-radius: 3px;
  padding: 8px;
  resize: none;
  /* max-height: 40vh;
  min-height: 200px; */
}

[name="classAdPrice"] {
  width: 20%;
  margin: 24px 20px 0px 20px;
}

.priceRelated {
  float: left;
  margin-bottom: 0px;
}

.priceWrapper {
  width: 100%;
  margin-top: 20px;
  height: inherit;
  overflow: auto;
}

.formButton {
  padding: 8px 15px;
  font-size: 16px;
  border-radius: 3px;
  margin-top: 22px;
  margin-right: 1.5%;
  float: right;
}


.important-button {
  color: white;
  background-color: #d80669;
  border: 1px solid #82003d;
  border-radius: 3px;
}

[name="cancel"] {
  color: black;
  background-color: #EBEBEB;
  border: 1px solid #AAA;

  /* background-color: inherit;
  color: #d80669;
  border-style: solid;
  border-color: transparent; */
}

.important-button:active {
  background-color: #82003d;
}

[name="cancel"]:active {
  background-color: #AAA;
}

.formButton:hover {
  cursor: pointer;
}


</style>
