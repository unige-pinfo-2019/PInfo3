<template>
  <div class="left-menu">
    <b-button class="new" variant="primary" to="/newad">Nouvelle annonce</b-button>

    <div class="categories">
      <div class="title">
        CATÉGORIES
      </div>
      <hr>
      <span class="category">Ordinateur</span>
      <span class="category">Habits homme</span>
      <span class="category">Habits femme</span>
      <span class="category">Livres</span>
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
export default {
  name: 'left-menu'
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
  // -webkit-appearance: none;
  // background: #862d86;
  // color: white;
  // border: 1px solid gray;
  margin: 30px 5px;
  height: 100%;
  align-self: center;
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

hr {
  border: 1px solid gray;
  width: 100%;
  margin: 10px 0px;
}

</style>
