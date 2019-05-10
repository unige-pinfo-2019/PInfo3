<template>
  <div class="detailed-ad">
    <div class="mycontainer">

      <div class="ad-rows">
        <div class="row-ad">
          <div class="block">
            <h1 class="title">{{this.title}}</h1>

            <div class="carousel-container">
              <b-carousel
              id="carousel-1"
              :interval="0"
              controls
              indicators
              background="#ababab"
              style="text-shadow: 1px 1px 2px #333;"
              >
              <!-- Text slides with image -->
              <!-- <b-carousel-slide
              caption="First slide"
              text="Nulla vitae elit libero, a pharetra augue mollis interdum."
              img-src="https://picsum.photos/480/1024/?image=52"
              ></b-carousel-slide> -->
              <b-carousel-slide v-if="images.length < 1">
                <img
                  slot="img"
                  class="d-block img-fluid w-100"
                  src="https://batanes.dost02.com/wp-content/themes/iloveit/images/no.image.600x300.png"
                  alt="image slot"
                  style="object-fit: contain; height: 400px;"
                >
              </b-carousel-slide>

              <b-carousel-slide v-for="image in images" v-bind:key="image">
                <img
                  slot="img"
                  class="d-block img-fluid w-100"
                  :src="image"
                  alt="image slot"
                  style="object-fit: contain; height: 400px;"
                >
              </b-carousel-slide>

              <!-- <b-carousel-slide>
                <img
                  slot="img"
                  class="d-block img-fluid w-100"
                  src="https://picsum.photos/1024/480/?image=55"
                  alt="image slot"
                  style="object-fit: contain; height: 400px;"
                >
              </b-carousel-slide> -->
            </b-carousel>
          </div>

          <p class="description">{{this.description}}</p>

          <p class="price">{{this.prix}} CHF</p>
        </div>
        </div>

        <div class="row-user">
          <div class="block user-container">
            <b-img src="https://i.stack.imgur.com/o1z7p.jpg" rounded="circle" center width="100px" height="100px"></b-img>

            <div class="user-name-container">
              <div class="username">
                Lena
              </div>
            </div>

          </div>

          <b-button class="buy-button" variant='primary'>Acheter</b-button>
        </div>
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
      title: "Je vend un pc très très bien",
      description: "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
      prix: "69",
      // images: [],
      images: ["https://picsum.photos/480/1024/?image=52", "https://picsum.photos/1024/480/?image=55"],
    }
  },
  mounted() {
    // TODO: Faire les requêtes axios pour charger les bonnes valeurs dans les
    // variables title, description, prix, images ci-dessus. Si il faut on peut
    // les renomer, faut juste les renomer dans la partie HTML ci-dessus.
    console.log('Need to do a request to: ' + this.id); // Cette ligne pour être supprimée une fois la requête implémentée
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style lang="scss" scoped>

.ad-rows {
  display: flex;
}

.row-ad {
  // flex-basis: 80%;
}

.row-user {
  flex-basis: 25%;
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

.block {
  width: 100%;
  // max-width: 800px;
  background: white;
  min-height: 100px;
  border-radius: 3px;
  border: 1px solid lightgrey;
  // padding: 20px 20px 20px 20px;
  margin-bottom: 30px;

  display: flex;
  flex-direction: column;
  align-items: flex-start;
}

h1 {
  margin-bottom: 20px;
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
  text-align: justify;
  margin-bottom: 20px;
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
