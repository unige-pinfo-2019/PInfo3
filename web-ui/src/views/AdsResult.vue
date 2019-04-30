<template>
  <div class="results">
    <div class="search-bar-wrapper">
      <Searchbar/>
    </div>

    <div class="ads-wrapper">
      <MiniAd title="Pc de collection à vendre" prix="9500" imgUrl="http://www.le-grenier-informatique.fr/medias/album/apple-iic-5.jpg" description="Je vend mon pc pour un bon prix, modèle Apple IIc je suis prêt à négocier le prix. N'hésitez pas à regarder tous les autres PC de collection que je vend depuis la vitrinne de mon profil. Allez bye les amis !"/>
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
    Searchbar
  }
}
</script>
