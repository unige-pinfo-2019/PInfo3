<template>
  <div class="new-ad">
    <div class="container">
      <div class="block">

        <span style="text-align: left;"> <h4>Titre</h4> </span>

        <div class="input">
          <b-form-input
          id="input-1"
          type="email"
          required
          placeholder="Entrez un titre"
          ></b-form-input>
        </div>

        <span class="field-title" style="text-align: left;"> <h4>Description</h4> </span>

        <div class="input">
          <b-form-textarea
            id="textarea-auto-height"
            rows="6"
            max-rows="12"
            required
            placeholder="Entrez une description"
          ></b-form-textarea>
        </div>

        <span style="text-align: left;"> <h4>Ajouter une image</h4> </span>

        <span style="text-align: left;">
          <b-button class="new-photo" variant="outline-primary"> <font-awesome-icon style="font-size: 2em;" icon="camera"/> </b-button>
        </span>

      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'new-ad'
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style lang="scss" scoped>
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 50px;
}

.block {
  width: 90%;
  max-width: 800px;
  background: white;
  min-height: 100px;
  border-radius: 3px;
  border: 1px solid lightgrey;
  padding: 20px;
}

h4 {
  align-self: start;
}


.input {
  margin-bottom: 30px;
}

.new-photo {
  float: left;
  width: 100px;
  height: 100px;
}

</style>
