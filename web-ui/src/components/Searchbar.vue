<template>
  <div class="search-bar-component">
    <div class="search-bar-wrapper">
      <input type="text" class="search-bar" value="" placeholder="Rechercher">
      <button type="button" class="search-button"><font-awesome-icon icon="search"/></button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'search-bar-component'
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style lang="scss" scoped>

.search-bar-component {
  // padding-right: 20px;
}

.search-bar-wrapper {
  // width: 100%;
  height: 50px;
  font-size: 1.5em;
  margin: 50px 0px;
  // margin-right: 30px;
  // background-color: black;
}

.search-bar {
  width: 90%;
  height: 100%;
  border: 1px solid lightgray;
  border-radius: 5px 0px 0px 5px;
  padding-left: 0.6em;
}

.search-bar:focus {
  outline: none;
  box-shadow: 0 0 3pt 2pt rgba($primary-color, 0.2);
  // outline: 5px solid rgba($primary-color, 0.2);
  border: 1px solid $primary-color;
}

.search-button {
  float: right;
  height: 100%;
  width: 10%;
  background: white;
  color: $primary-color;
  border: 1px solid lightgrey;
  border-left: none; /* Prevent double borders */
  border-radius: 0px 5px 5px 0px;
}

.search-button:hover {
  outline: none;
  background: $primary-color;
  color: white;
  border: 1px solid $primary-color;
}

.search-button:focus {
  outline: none;
  // border: 1px solid $primary-color;
}



// .search-div {
//   margin: 50px 0px;
//   width: 100%;
// }
</style>
