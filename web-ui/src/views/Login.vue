<template>
  <!-- <div class="container">
    <div class="notification">
      <section>
          <form @submit.prevent="validateBeforeSubmit">

              <b-field label="Nom d'utilisateur"
                  :type="{'is-danger': errors.has('username')}"
                  :message="errors.first('username')">
                  <b-input type="text" v-model="username" name="username" v-validate="'required|alpha'" />
              </b-field>

              <b-field label="Mot de passe"
                  :type="{'is-danger': errors.has('password')}"
                  :message="errors.first('password')">
                  <b-input type="password" v-model="password" name="password" v-validate="'required|min:8'" />
              </b-field>

              <div class="block submit-flex">
                <b-button type ="submit" style="float: right;"  variant="primary">Soumettre</b-button>
              </div>
          </form>
      </section>
    </div>
  </div> -->

  <div class="login">
    <h1>Connexion</h1>
    <div class="field-wrapper">
      <b-form-input size="lg" placeholder="Nom d'utilisateur" class="username" v-model="username"></b-form-input> 
      <b-form-input size="lg" placeholder="Mot de passe" class="password" v-model="password"></b-form-input>
    </div>
    <b-button size="lg" variant="primary">Se connecter</b-button>
  </div>


</template>


<script>
    import Vue from 'vue'
    // import VeeValidate from 'vee-validate';
    // import { Validator } from 'vee-validate';
    //
    // const dict = {
    //   custom: {
    //     username: {
    //       required: 'Le champ nom d\'utilisateur est requis',
    //       alpha: 'Uniquement des caractères alphanumériques'
    //     },
    //     password: {
    //       required: 'Le champ mot de passe est requis',
    //       min : 'Le mot de passe doit avoir au moins 8 caractères'
    //     }
    //
    //
    //   }
    // };
    //
    // Validator.localize('en', dict);
    // Vue.use(VeeValidate)

    export default {
        name : 'login',

        data() {
            return {
                username: null,
                password: null,

            }
        },
        // methods: {
        //     validateBeforeSubmit() {
        //         this.$validator.validateAll().then((result) => {
        //             if (result) {
        //                 this.$toast.open({
        //                     message: 'Formulaire valide!',
        //                     type: 'is-success',
        //                     position: 'is-bottom'
        //                 })
        //                 return;
        //             }
        //             this.$toast.open({
        //                 message: 'Formulaire invalide!',
        //                 type: 'is-danger',
        //                 position: 'is-bottom'
        //             })
        //         });
        //     }
        // }
    }
</script>


<style lang="scss" scoped>
.login {
  padding-top: 100px;
  display: flex;
  flex-direction: column;
  align-items: center;
  // justify-content: center;
}

.fields-wrapper {
  display: block;
  margin-left: auto;
  margin-right: auto;
}

.username {
  // flex-basis: 200px;
  margin-bottom: 40px;
  width: 400px;
}

.password {
  // flex-basis: 200px;
  margin-bottom: 60px;
  width: 400px;
}

h1 {
  margin-bottom: 60px;
}

</style>
