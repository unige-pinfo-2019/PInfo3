<template>
  <div class="minimized-ad">
    <b-card
      v-bind:title="shortenedTitle"
      v-bind:img-src="imgUrl"
      img-alt="Image"
      img-top
      tag="article"
      style="max-width: 20rem;"
      class="mb-2"
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
    prix: String
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
      if (this.title.length > 25) {
        this.shortenedTitle = this.title.substring(0, 24) + "...";
      }
      // console.log("Title length: " + this.title.length)
    },
    shortenDescription() {
      if (this.description.length > 125) {
        this.shortenedDescription = this.description.substring(0, 124) + "...";
      }
      // console.log(this.description.length)
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style lang="scss" scoped>
#descr {
  color: #666;
}

#price {
  float: right;
  margin-right: 10px;
  font-weight: bold;
  // font-size: 1.4em;
}

// #price-name {
//   float: left;
//   margin-bottom: 0px;
//   color: #AAA;
// }

.price-wrapper {
  margin-top: 30px;
  font-size: 1.3em;
}
</style>
