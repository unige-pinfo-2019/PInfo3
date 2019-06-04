<template>
  <div class="conversations">
    <span> <h4> Messages </h4> </span>
    <div class="message" v-for="msg in messages.data">
      <b v-if="msg.flag === true"> Moi :  </b>
      <b v-else > Vendeur :  </b>

      {{ msg.response }}
    </div>
    <span> <h4>Nouveau message</h4>  </span>

    <div class="input">
      <b-form-textarea v-model="newMessage"
        id="textarea-auto-height"
        rows="6"
        max-rows="12"
        placeholder="Veuillez entrer un nouveau message"
        required
      ></b-form-textarea>
  </div>
  <b-button style="float: right;"  v-on:click="submit" variant="primary">Soumettre</b-button>

</div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'showconversation',
  props: {
    userId:{
      type:Number,
      required:false,
      default: 1
    },
    adId : {
      type:Number,
      required:false,
      default: 1
    }
  },
  data () {
    return {
      messages : null,
      newMessage : ''
    }
  },
  mounted: async function() {
    this.update();
  },
  methods: {
    update: async function() {
      this.messages = await axios.get(process.env.VUE_APP_BASE_API + ':8085/classadsresponses/users/' + this.userId + '/ads/' + this.adId +'?offset=0&limit=10');
    },
    submit: async function(event) {
      var data = {"adID" : this.adId,
                   "userID": this.userId,
                   "response": this.newMessage,
                  "flag": false};
      await axios.post(process.env.VUE_APP_BASE_API + ':8085/classadsresponses/users/' + this.userId + '/ads/' + this.adId , data);
      this.update();
    }
  }
}
</script>
