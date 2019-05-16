<template>
  <div class="minimized-ad">
    <div class="clicker" v-on:click="alert('Clicked')">

      <b-card v-if="title !== ''"
      v-bind:title="shortenedTitle"
      v-bind:img-src="imgUrl"
      img-alt="Image"
      img-top
      tag="article"
      style="max-width: 18rem;"
      class="mb-2 card"
      >

      <a v-on:click="changePage(id)"></a>
      <b-card-text id="descr">
        {{ shortenedDescription }}
      </b-card-text>

      <div class="price-wrapper">
        <b-card-text id="price">
          CHF {{ prix }}
        </b-card-text>
      </div>

    </b-card>
    </div>



    <div v-if="title == ''" style="width: 18rem">
    </div>
  </div>
</template>

<script>
export default {
  name: 'minimized-ad',
  props: {
    imgUrl: String, //https://picsum.photos/600/300/?image=25
    title: String,
    description: String,
    prix: Number,
    id: Number
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
      var max_length = 42;

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
      //console.log(this.description.length)
    },
    redirect() {
      alert(3)
    },
    changePage(id) {
      // alert(id)
      this.$router.push('/ad/' + id)
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style lang="scss" scoped>

.card-img-top { // Forces the card image to be of the same size
    width: 100%;
    height: 12rem;
    object-fit: cover;
}

a {
  position: absolute;
  top: 0;
  left: 0;
  height: 100%;
  width: 100%;
}

a:hover {
  cursor: pointer;
}

.card-title {
  height: 2.2em;
}

#descr {
  color: #666;
  text-align: justify;
  letter-spacing: -0.01em;
  height: 120px;
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
