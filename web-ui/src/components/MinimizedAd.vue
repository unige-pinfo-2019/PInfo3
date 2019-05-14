<template>
  <div class="minimized-ad">
    <b-card
      v-bind:title="shortenedTitle"
      v-bind:img-src="imgUrl"
      img-alt="Image"
      img-top
      tag="article"
      style="max-width: 20rem;"
      class="mb-2 card"
    >
      <b-card-text id="descr">
        <!-- Some quick example text to build on the card title and make up the bulk of the card's content. -->
        {{ shortenedDescription }}
      </b-card-text>

      <div class="price-wrapper">
        <b-card-text id="price">
          CHF {{ prix }}
        </b-card-text>
      </div>

      <!-- <b-button href="#" variant="outline-primary">Go somewhere</b-button> -->
    </b-card>
  </div>
</template>

<script>
export default {
  name: 'minimized-ad',
  props: {
    imgUrl: String, //https://picsum.photos/600/300/?image=25
    title: String,
    description: String,
    prix: Number
  },
  data() {
    return {
      shortenedDescription: null,
      shortenedTitle: null
    }
  },
  mounted() {
    this.shortenTitle();
    this.shortenDescription();
  },
  methods: {
    shortenTitle() {
      var max_length = 25;

      if (this.title.length > max_length) {
        this.shortenedTitle = this.title.substring(0, max_length-1) + "...";
      }
      else {
        this.shortenedTitle = this.title;
      }
      // console.log("Title length: " + this.title.length)
    },
    shortenDescription() {
      var max_length = 190;

      if (this.description.length > max_length) {
        this.shortenedDescription = this.description.substring(0, max_length-1) + "...";
      }
      else {
        this.shortenedDescription = this.description;
      }
      console.log(this.description.length)
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style lang="scss" scoped>
#descr {
  color: #666;
  text-align: justify;
  letter-spacing: -0.01em;
}

#price {
  float: right;
  margin-right: 10px;
  font-weight: bold;
  // font-size: 1.4em;
}

.card {
  text-align: left;
  width: 600px;
}

.price-wrapper {
  margin-top: 30px;
  font-size: 1.3em;
}

.minimized-ad {
  margin-bottom: 40px; // Temporaire, pour faire joli
}
</style>
