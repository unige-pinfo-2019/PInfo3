<template>
  <div class="minimized-ad">
    <b-button to="/login" id="in" class="login" variant="light">Se connecter</b-button>
    <b-button to="/signup" id="up" class="login" variant="outline-light">S'inscrire</b-button>
  </div>
</template>

<script>
export default {
  name: 'login'
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style lang="scss" scoped>
.login {
  margin-right: 20px;
}

#in {
  color: #ea5a5f;
}

#up:hover {
  color: #ea5a5f;
}

</style>
