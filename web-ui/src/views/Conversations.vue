<template>
  <div class="conversations">
    <div class="message" v-for="msg in messages.data">
    {{ msg }}
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'conversations',
  props: {
    userId:{
      type:String,
      required:false,
      default:"1"
    },
    adId : {
      type:String,
      required:false,
      default:"1"
    }
  },
  data () {
    return {
      messages : null
    }
  },
  mounted: async function() {
    this.messages = await axios.get(process.env.VUE_APP_BASE_API + ':8085/classadsresponses/users/' + this.userId)
    // retrieve conversations for user userId
  }
}
</script>
