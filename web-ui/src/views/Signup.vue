<template>
  <div class="container">
    <div class="notification">
      <section>
          <form @submit.prevent="validateBeforeSubmit">

              <b-field label="Prénom"
                  :type="{'is-danger': errors.has('firstname')}"
                  :message="errors.first('firstname')">
                  <b-input v-model="firstname" name="firstname" v-validate="'required'" />
              </b-field>

              <b-field label="Nom de famille"
                  :type="{'is-danger': errors.has('lastname')}"
                  :message="errors.first('lastname')">
                  <b-input v-model="lastname" name="lastname" v-validate="'required'" />
              </b-field>

              <b-field label="Email"
                  :type="{'is-danger': errors.has('email')}"
                  :message="errors.first('email')">
                  <b-input v-model="email" name="email" v-validate="'required|email'" />
              </b-field>

              <b-field label="Nom d'utilisateur"
                  :type="{'is-danger': errors.has('username')}"
                  :message="errors.first('username')">
                  <b-input type="text" v-model="username" name="username" v-validate="'required|alpha'" />
              </b-field>

              <b-field label="Mot de passe (au moins 8 caractères)"
                  :type="{'is-danger': errors.has('password')}"
                  :message="errors.first('password')">
                  <b-input type="password" v-model="password" name="password" v-validate="'required|min:8'" />
              </b-field>

              <b-field label="Confirmer le mot de passe"
                  :type="{'is-danger': errors.has('confirm-password')}"
                  :message="[{
                      'Veuillez confirmer le mot de passe' : errors.firstByRule('confirm-password', 'required'),
                      'Le mot de passe est invalide' : errors.firstByRule('confirm-password', 'is')
                  }]">
                  <b-input type="password" v-model="confirmPassword" name="confirm-password"
                      v-validate="{ required: true, is: password }" />
              </b-field>

              <b-field label=""
                  :type="{'is-danger': errors.has('flag-terms')}"
                  :message="{'Veuillez cocher pour continuer' : errors.firstByRule('flag-terms', 'required')}">
                  <b-checkbox v-model="flagTerms" name="flag-terms" v-validate="'required:false'">
                      Je consents aux <strong><a href="/conditions">CGU</a></strong>

                  </b-checkbox>
              </b-field>

              <button type="submit" class="button is-primary"> Inscription </button>
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
        email: {
          required: 'Le champ email est requis',
          email : 'Votre email est invalide'
        },
        firstname: {
          required: 'Le champ prénom est requis'
        },
        lastname: {
          required: 'Le champ nom est requis'
        },
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
        name : 'signup',

        data() {
            return {
                firstname: null,
                lastname: null,
                email: null,
                age: null,
                username: null,
                password: null,
                confirmPassword: null,
                gender: null,
                question: null,
                flagTerms: false
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
