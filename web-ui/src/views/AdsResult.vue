<template>
  <div class="results">

    <div class="search-bar-wrapper">
      <Searchbar v-on:clicked="onClickSearch"/>
    </div>
    <div class="results-wrapper">
      <template v-if="ads !=null">
        <MiniAd v-for="ad in ads.data" :title="ad.title" :prix="ad.price" imgUrl="http://www.le-grenier-informatique.fr/medias/album/apple-iic-5.jpg" :description="ad.description" v-bind:key="ad.title"/>
      </template>
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
  data() {
    return {
      ads : null,
      query: null
    }
  },
  methods: {
    onClickSearch (value) {
      this.query=value;
      axios
        .get('http://localhost:8084/search?request=' + this.query)
        .then(response => (this.ads = response));
      this.$forceUpdate();
    }
  },
  mounted: function () {
    // retrieve list of categories
    axios
      .get('http://localhost:8081/classads')
      .then(response => (this.ads = response));
  }
}
</script>

<style lang="scss" scoped>
.results-wrapper {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  flex-wrap: wrap;
}
</style>
