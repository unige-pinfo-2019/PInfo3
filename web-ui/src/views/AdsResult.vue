<template>
  <div class="results">

    <div class="search-bar-wrapper">
      <Searchbar v-on:clicked="onClickSearch"/>
    </div>

    <div class="results-pub-container">

      <div class="results-wrapper">
        <template v-if="ads !=null">
          <MiniAd class="mini-ad" v-for="ad in ads.data" :title="ad.title" :prix="ad.price" :id="ad.id" imgUrl="http://www.le-grenier-informatique.fr/medias/album/apple-iic-5.jpg" :description="ad.description" v-bind:key="ad.title"/>

          <MiniAd class="mini-ad" v-for="index in requiredEmpty" :key="index" title="" :prix=0 :id="0" imgUrl="" description="blank"/>
        </template>
      </div>

      <div class="pub-wrapper">
        <div class="pub">
          pub placeholder
        </div>
      </div>

    </div>


  </div>
</template>

<script>
// @ is an alias to /src
import MiniAd from '@/components/MinimizedAd.vue'
import Searchbar from '@/components/Searchbar.vue'
import axios from 'axios';


export default {
  name: 'results',
  components: {
    MiniAd,
    Searchbar
  },
  props: {
    id:{
      type:String,
      required:false
    }
  },
  data() {
    return {
      ads : null,
      query: null,
      requiredEmpty: 11
    }
  },
  methods: {
    onClickSearch (value) {
      this.query=value;
      axios
        .get('http://localhost:8084/search?request=' + this.query)
        .then(response => (this.ads = response));
      this.$forceUpdate();
    },
    update () {
      // retrieve list of ads
      axios
        .get('http://localhost:8081/classads/categories/' + this.id)
        .then(response => (this.ads = response));
    }
  },
  updated: function() {
    this.update();
  },

  mounted: function () {
    if (this.id) {
      this.update();
    }
    else {
      // retrieve list of ads
      axios
        .get('http://localhost:8081/classads')
        .then(response => (this.ads = response));
        this.$forceUpdate();

    }
  }
}
</script>

<style lang="scss" scoped>
.results-pub-container {
  display: flex;
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
  height: 600px;
  background: gray;
  color: lightgray;

  margin-bottom: 30px;
}




</style>
