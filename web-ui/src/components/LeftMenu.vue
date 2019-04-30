<template>
  <div class="left-menu">
    <b-button class="new" variant="primary" to="/newad">Nouvelle annonce</b-button>
    <!-- <button type="button" class="new">Nouvelle annonce</button> -->
    <b-list-group>
      <!-- <b-list-group-item button active>Poster une annonce</b-list-group-item> -->
      <b-list-group-item button>Ordinateurs</b-list-group-item>
      <b-list-group-item button>Habits homme</b-list-group-item>
      <b-list-group-item button>Habits femme</b-list-group-item>
      <b-list-group-item button>Livres</b-list-group-item>
    </b-list-group>
  </div>
</template>

<script>
export default {
  name: 'left-menu'
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style lang="scss" scoped>
.new {
  // -webkit-appearance: none;
  // background: #862d86;
  // color: white;
  // border: 1px solid gray;
  margin: 15px 5px 15px 5px;
  height: 90%;
}
</style>
