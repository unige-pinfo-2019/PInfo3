<template>
  <div class="home">
  <div class="tile is-ancestor">
    <div class="tile is-parent is-3">
      <div class="tile is-child box">
        <p class="title">Categories</p>
        <menut></menut>
      </div>
    </div>
  <div class="tile is-9 is-vertical is-parent">
    <div class="tile is-child box">
      <section class="section">
 <div class="container">
   <h1 class="title">Recherche : </h1>
   <h2 class="subtitle">
   </h2>
 </div>
</section>
<section class="section">
<div class="container">
<h1 class="title">Dernières annonces : </h1>
<h2 class="subtitle">
Liste des <strong> dernières annonces </strong></h2>
</div>
</section>
    </div>

  </div>
</div>
</div>
</template>

<script>

import Menu from "@/components/Menu";


export default {
  name: "home",
  components: {
    'menut' : Menu
  }
};


</script>
