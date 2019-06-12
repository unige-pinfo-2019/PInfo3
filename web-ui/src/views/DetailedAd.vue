<template>
  <div class="detailed-ad">
    <div class="mycontainer">

      <div class="ad-rows">
        <div class="row-ad">
          <div class="block col-flex">
            <h1 class="title">{{this.res.data.title}}</h1>

            <div class="username">
              Par <span style="color: black; text-decoration: underline;">{{this.res.data.username}}</span>
            </div>

            <div class="carousel-container">
              <b-carousel
              id="carousel-1"
              :interval="0"
              controls
              indicators
              background="#ababab"
              style="text-shadow: 1px 1px 2px #333;"
              >

              <!-- If no images in the ad, we display the default 'no image' picture-->
              <b-carousel-slide v-if="this.res.data.images.length < 1">
                <img
                  slot="img"
                  class="d-block img-fluid w-100"
                  src="https://batanes.dost02.com/wp-content/themes/iloveit/images/no.image.600x300.png"
                  alt="image slot"
                  style="object-fit: contain; height: 400px;"
                >
              </b-carousel-slide>

              <!-- Displays all images of the ad in a carousel -->
              <b-carousel-slide v-for="image in this.res.data.images" v-bind:key="image">
                <img
                  slot="img"
                  class="d-block img-fluid w-100"
                  :src="image"
                  alt="image slot"
                  style="object-fit: contain; height: 400px;"
                >
              </b-carousel-slide>


            </b-carousel>
          </div>

          <p class="description">{{this.res.data.description}} </p>

          <p class="price">{{this.res.data.price}} CHF</p>
        </div>

        <!-- Delete and edit buttons -->
        <div class="block button-container">
          <div v-if="this.res.data.auth">
            <b-button v-b-modal.you-sure class="delete-btn" variant="danger"><font-awesome-icon class="icon" icon="trash-alt"/>Supprimer</b-button>
            <b-button class="edit-btn" variant="primary"><font-awesome-icon class="icon" icon="edit"/>Éditer</b-button>
          </div>
          <div v-else>
            <b-button variant="primary" class="buy-btn" size="lg">Acheter</b-button>
          </div>



          <b-modal id="you-sure" centered v-model="showModal">
            <template slot="modal-title">
              Confirmation
            </template>
            <div style="display: block;">

              Êtes-vous sûr de vouloir supprimer l'annonce? Cette opération n'est pas réversible.

            </div>
            <div slot="modal-footer" class="w-100">
              <b-button variant="primary" class="float-right" v-on:click="showModal=false; deleteThisAd()">
                Supprimer
              </b-button>
              <b-button variant="secondary" style="margin-right: 20px" class="float-right" @click="showModal=false">
                Annuler
              </b-button>
            </div>
          </b-modal>
        </div>
        </div>


        <!-- <div class="row-user">
          <div class="block user-container">
            <b-img src="https://i.stack.imgur.com/o1z7p.jpg" rounded="circle" center width="100px" height="100px"></b-img>

            <div class="user-name-container">
              <div class="username">
                Lena
              </div>
            </div>

          </div>

          <b-button class="buy-button" variant='primary'>Acheter</b-button>
        </div> -->
      </div>



    </div>
  </div>
</template>

<script>

export default {
  name: 'detailed-ad',
  props: {
    id: String,
  },
  data() {
    return {
      showModal: false,
      res: null
    }
  },
  mounted() {
    // retrieve ad
    this.$axios
      .get(process.env.VUE_APP_BASE_API + ':8081/classads/ads/ad/' + this.id)
      .then((response) => {
        this.res = response
        console.log('data.auth value:');
        console.log(this.res.data.auth);
      });

  },
  methods: {
    deleteThisAd() {
      this.$axios
      .delete(process.env.VUE_APP_BASE_API + ':8081/classads/ads/ad/' + this.id)
      .then((response) => {
        this.$router.push("/")
        // console.log(response); // Succès !
      }, (reason) => {
        console.log(reason); // Erreur !
      });
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style lang="scss" scoped>

.username {
  color: gray;
  margin-left: 20px;
  margin-bottom: 20px;
  font-size: 1.1em;
}

.icon {
  margin-right: 0.5em;
}

.delete-btn {
  float: right;
}

.edit-btn {
  margin-right: 20px;
  float: right;
}
.buy-btn {
  float: right;
}

.button-container {
  padding: 20px;
  display: inline;
  // display: flex;
  // flex-direction: row;
  // justify-content: flex-end;
  // align-items: center;
}

.col-flex  {
  display: flex;
  flex-direction: column;
  align-items: flex-start;

}

.block {
  width: 100%;
  // max-width: 800px;
  background: white;
  // min-height: 100px;
  border-radius: 3px;
  border: 1px solid lightgrey;
  // padding: 20px 20px 20px 20px;
  margin-bottom: 30px;

}

.ad-rows {
  display: flex;
  flex-direction: row;
}

.row-ad {
  // flex-basis: 80%;
  // flex-basis: 75%;
  // flex-grow: 75%;
  // flex-shrink: 75%;
  width: 700px;
  // flex-grow: 5;
  // flex-shrink: 3;
  // flex-basis: 100%;

  display: flex;
  flex-direction: column;
}

.row-user {
  // flex-basis: 100px;
  // flex-grow: 1;
  // flex-shrink: 3;
  // flex-basis: auto;
  margin-left: 30px;
}


.mycontainer {
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  // margin: 0;
  margin-top: 50px;
}

.user-container {
  padding: 20px;
}

.user-name-container {
  margin-top: 10px;
  width: 100%;

  display: flex;
  flex-direction: column;
  align-items: stretch;
  justify-content: center;
  // text-align: center;
}

.user-name {
  text-align: center;
  padding-bottom: 0px;
}



.carousel-container {
  align-self: center;
  margin-bottom: 20px;
  width: 100%;
}



.title {
  text-align: left;
}

.description {
  // align-self: flex-start;
  text-align: left;
  margin-bottom: 20px;
  white-space: pre-wrap;
}

.price {
  align-self: flex-end;
  font-size: 1.5em;
  // text-align: right;
  margin-bottom: 0px;
}

.title, .price, .description {
  padding: 20px;
}

.buy-button {
  width: 100%;
  height: 50px;
}


</style>
