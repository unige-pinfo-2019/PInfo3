<template>
  <div class="footer">
    <footer>
      <b-container class="column-wrapper">
        <b-row>
          <b-col cols="4">
            <h4>ByteTheDust</h4>
            <hr>
            <p>Ce site a été réalisé dans le cadre du cours Projet Informatique de l'université de Genève par l'équipe ByteTheDust.</p>

          </b-col>
          <!-- <b-col>2 of 3</b-col> -->
          <b-col cols="8">
            <h4>Équipe</h4>
            <hr>

            <b-row>
             <b-col>
               <ul>
                 <li>Aurélia Autem</li>
                 <li>Gibran Chevalley</li>
                 <li>Attila Nacsa</li>
               </ul>
               <!-- <p>Aurélia Autem</p>
               <p>Gibran Chevalley</p>
               <p>Attila Nacsa</p> -->
             </b-col>
             <b-col>
               <li>Guy-Raphaël Stauffer</li>
               <li>Pavlos Tserevelakis</li>
               <li>David alexander</li>
             </b-col>
            </b-row>

          </b-col>
        </b-row>
      </b-container>
      <div class="bottom-date">
          <p class="bottom-date-text">2019 - Genève</p>
      </div>
    </footer>
  </div>
</template>

<script>
export default {
  name: 'footer'
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style lang="scss" scoped>
footer {
  background: $dark-color;
  color: white;
}

.column-wrapper {
  padding-top: 30px;
  text-align: left;
}

p, li {
  color: $light-gray;
}

hr {
  border: 1px solid white;
}

.bottom-date {
  background: #333;
  text-align: center;
  color: $light-gray;
  // height: 40px;
  line-height: 40px;
}

.bottom-date-text {
  margin-bottom: 0px;
}
</style>
