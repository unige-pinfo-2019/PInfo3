<template>
  <div class="container">
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

              <button type="submit" class="button is-primary"> Connection </button>
          </form>
      </section>
    </div>
  </div>
</template>


<script>
    import Vue from 'vue'
    import VeeValidate from 'vee-validate';
    import { Validator } from 'vee-validate';

    const dict = {
      custom: {
        username: {
          required: 'Le champ nom d\'utilisateur est requis',
          alpha: 'Uniquement des caractères alphanumériques'
        },
        password: {
          required: 'Le champ mot de passe est requis',
          min : 'Le mot de passe doit avoir au moins 8 caractères'
        }


      }
    };

    Validator.localize('en', dict);
    Vue.use(VeeValidate, {
        events: ''
    })

    export default {
        name : 'login',

        data() {
            return {
                username: null,
                password: null,

            }
        },
        methods: {
            validateBeforeSubmit() {
                this.$validator.validateAll().then((result) => {
                    if (result) {
                        this.$toast.open({
                            message: 'Formulaire valide!',
                            type: 'is-success',
                            position: 'is-bottom'
                        })
                        return;
                    }
                    this.$toast.open({
                        message: 'Formulaire invalide!',
                        type: 'is-danger',
                        position: 'is-bottom'
                    })
                });
            }
        }
    }
</script>
