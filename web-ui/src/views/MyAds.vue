<template>
  <div class="results">

    <!-- <div class="search-bar-wrapper">
      <Searchbar v-on:clicked="onClickSearch"/>
    </div> -->

    <h1 style="text-align: left; margin-top: 50px;">Mes annonces</h1>

    <div class="results-pub-container">

      <div class="results-wrapper">
        <template v-if="ads !=null">
          <MiniAd class="mini-ad" v-for="ad in ads.data" :title="ad.title" :prix="ad.price" :id="ad.id" :imgUrl="ad.images" :description="ad.description" v-bind:key="ad.title"/>

          <MiniAd class="mini-ad" v-for="index in requiredEmpty" :key="index" title="" :prix=0 :id="0" :imgUrl="[]" description="blank"/>
        </template>
      </div>

      <div class="pub-wrapper">
        <!-- <div class="pub">
          pub placeholder
        </div> -->
        <img :src="pubSrc[pubIndex]" alt="advertisement placeholder">
      </div>

    </div>


  </div>
</template>

<script>
// @ is an alias to /src
import MiniAd from '@/components/MinimizedAd.vue'
import Searchbar from '@/components/Searchbar.vue'

export default {

  name: 'results',
  components: {
    MiniAd,
    // Searchbar
  },
  props: {
    id:{
      type:String,
      required:false,
      default:"-1"
    }
  },
  data() {
    return {
      ads : null,
      query: null,
      requiredEmpty: 3,
      pubSrc: [require('../assets/pub2.png'), require('../assets/pub3.png'), require('../assets/pub1.png')],
      pubIndex: 0,
    }
  },
  methods: {
    incrementCount() {
      this.pubIndex = (this.pubIndex + 1) % this.pubSrc.length
    }
    // onClickSearch (value) {
    //   this.query=value;
    //   this.$axios
    //     .get(process.env.VUE_APP_BASE_API + ':8084/search?request=' + this.query)
    //     .then(response => (this.ads = response));
    //   this.$forceUpdate();
    // },
    // update () {
    //   if (this.id >= 0)
    //   {
    //     // retrieve list of ads
    //     this.$axios
    //       .get(process.env.VUE_APP_BASE_API + ':8081/classads/categories/' + this.id)
    //       .then(response => (this.ads = response));
    //   }
    //   else {
    //     // retrieve list of ads
    //     this.$axios
    //       .get(process.env.VUE_APP_BASE_API + ':8081/classads/')
    //       .then(response => (this.ads = response));
    //   }
    // }
  },

  // watch: {
  //   id: function() {
  //     this.update();
  //   }
  // },
  //
  // beforeRouteUpdate(to, from, next) {
  //   this.update();
  //   next()
  // },

  mounted: function () {

    setInterval(this.incrementCount, 15000)

    // this.update();
    this.$axios
      .get(process.env.VUE_APP_BASE_API + ':8081/classads/user/')//) + this.$myStore.userid)
      .then(response => {
        this.ads = response
        console.log('Ads received');
      });
  }
}
</script>

<style lang="scss" scoped>
.results-pub-container {
  display: flex;
  margin-top: 50px;
}

@media screen and (max-width: 1070px) { // Screens smaller or equal to 1070px width
  .pub-wrapper {
    display: none;
  }

  .results-wrapper {
    // flex-grow: 100;

    display: flex;
    flex-direction: row;
    justify-content: space-between;
    flex-wrap: wrap;
  }

}

@media screen and (min-width: 1071px) { // Screens bigger or equal to 1071px width
  .results-wrapper {
    flex-grow: 100;

    display: flex;
    flex-direction: row;
    // justify-content: space-between;
    flex-wrap: wrap;
  }

  .mini-ad {
    margin-right: auto;
  }
}

.pub-wrapper {
  max-width: 200px;
  min-width: 200px;
  // flex-basis: 200px;
  // flex-shrink: 200px;
  // height: 600px;
  // background: gray;
  color: lightgray;

  margin-bottom: 50px;
}




</style>
