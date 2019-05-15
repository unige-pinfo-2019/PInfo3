<template>
  <div class="left-menu">
    <b-button block class="new" variant="primary" to="/newad"><font-awesome-icon icon="plus"/><span class="new-label">Nouvelle annonce</span></b-button>

    <div class="categories">
      <div class="title">
        CATÉGORIES
      </div>
      <hr>
      <div v-for="cat in categories.data" v-bind:key="cat.name">
        <TreeMenu :label="cat.name" :nodes="cat.children" :depth="0"></TreeMenu>
      </div>

      <!-- <span class="category">Ordinateur</span>
      <span class="category">Habits homme</span>
      <span class="category">Habits femme</span>
      <span class="category">Livres</span>  -->
      <!-- <TreeMenu :label="categories.name" :nodes="categories.children"></TreeMenu> -->

    </div>
    <!-- <button type="button" class="new">Nouvelle annonce</button> -->
    <!-- <b-list-group>
      <b-list-group-item button>Ordinateurs</b-list-group-item>
      <b-list-group-item button>Habits homme</b-list-group-item>
      <b-list-group-item button>Habits femme</b-list-group-item>
      <b-list-group-item button>Livres</b-list-group-item>
    </b-list-group> -->
  </div>
</template>

<script>
import TreeMenu from '@/components/TreeMenu.vue'
import axios from 'axios';

export default {
  name: 'left-menu',
  components: {
    TreeMenu
  },
  data() {
    return {
      categories: null
    }
  },
  mounted: function () {
    // retrieve list of categories
    axios
      .get('http://localhost:8081/categories/treeview')
      .then(response => (this.categories = response));

  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style lang="scss" scoped>
.left-menu {
  display: flex;
  flex-direction: column;
  align-items: stretch;
  margin-left: 30px;
}

.new {
  font-size: 1.2em;

  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-evenly;

  // -webkit-appearance: none;
  // background: #862d86;
  // color: white;
  // border: 1px solid gray;
  margin: 50px 5px;
  height: 100%;
  align-self: center;
  flex-basis: 50px;

  // vertical-align: middle;
}

.categories {
  padding: 10px 15px;
  background: white;

  display: flex;
  flex-direction: column;

  border-radius: 3px;
  border: 1px solid lightgray;
}

.title {
  font-size: 1.3em;
  font-weight: bold;
}

.category {
  padding: 2px 0px;
}

.category:hover {
  color: $primary-color;
}

hr {
  border: 1px solid gray;
  width: 100%;
  margin: 10px 0px;
}

</style>
