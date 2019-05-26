<template>
  <div class="minimized-ad">
    <div class="clicker" v-on:click="alert('Clicked')">

      <b-card v-if="title !== ''"
      v-bind:img-src="imgUrl"
      img-alt="Image"
      img-top
      tag="article"
      style="max-width: 18rem;"
      class="mb-2 card"
      >

      <a v-on:click="changePage(id)"></a>
      <b-card-title>
        <div class="title-container">
          {{title}}
        </div>
      </b-card-title>

      <b-card-text id="descr">
        <!-- {{ description }} -->
        <div class="descr-container">
          {{description}}
          <!-- Lorem ipsum dolor sit amet, consectetuer addksiojke kdoewpkdewiop  kdoewpk dopewk pdoewk dopw kpoedeipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus -->
        </div>
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
    // this.shortenTitle();
    // this.shortenDescription();
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
      this.shortenedTitle = this.title;
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
      this.shortenedDescription = this.description;
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

/* mixin for multiline */
@mixin multiLineEllipsis($lineHeight: 1.2em, $lineCount: 1, $bgColor: white){
  overflow: hidden;
  position: relative;
  line-height: $lineHeight;
  max-height: $lineHeight * $lineCount;
  text-align: justify;
  // margin-right: -1em;
  padding-right: 1em;
  &:before {
    content: '...';
    position: absolute;
    right: 0;
    bottom: 0;
  }
  &:after {
    content: '';
    position: absolute;
    right: 0;
    width: 1em;
    height: 1em;
    margin-top: 0.2em;
    background: $bgColor;
  }
}

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
  // height: 2.2em;
  min-height: 2.2em;
  @include multiLineEllipsis($lineHeight: 1.2em, $lineCount: 2, $bgColor: white);
}

.title-container {
  font-size: 0.8em;
  font-weight: bold;
}

#descr {
  color: #666;
  text-align: justify;
  // letter-spacing: -0.01em;
  height: 120px;
}

.descr-container {
  letter-spacing: -0.03em;
  @include multiLineEllipsis($lineHeight: 1.2em, $lineCount: 7, $bgColor: white);
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
