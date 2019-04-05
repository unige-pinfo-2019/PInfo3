<template>
  <div id="myApplication">
    <!-- <span class="newAd">
      <button type="button" name="newAd" v-on:click="openForm">NEW</button>
    </span> -->

    <!-- <transition name="form-in"> -->
      <div class="adForm">
        <NewAdForm v-if="showForm"/>
      </div>
    <!-- </transition> -->

    <footer>
     <p class="footer-infos">Test project PInfo3</p>
     <p class="footer-infos">2019</p>
    </footer>
  </div>
</template>

<script>
import NewAdForm from './components/NewAdForm.vue'

export default {
  name: 'app',
  components: {
    NewAdForm
  },
  data() {
    return {
      showForm: true
    }
  },
  methods: {
    openForm() {
      this.showForm = true
    }
  }
}
</script>

<style>
#app {
  text-align: center;
  color: #2c3e50;
}

#myApplication {
  display: block;
}

.newAd {
  display: flex;
  justify-content: center;
  align-items: center;
}

.adForm {
  /* display: flex;
  justify-content: center;
  align-items: center; */
  display: block;
  margin: 0 auto;
  max-width: 700px;
}

footer {
  background: #f5f5f5;
  text-align: center;
  color: #888;
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  font-size: 16px;
  padding: 1px 0px;
  margin-top: 30px;
  width: 100%;
}

.footer-infos {
  margin: 10px 0px;
}

</style>
