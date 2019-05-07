<template>
<div class ="Upload">
<UploadForm/>
</div>
</template>

<script>
// @ is an alias to /src
import UploadForm from '@/components/UploadForm.vue'


export default {
  name: 'upload',
  components: {
    UploadForm,
  }
}
</script>
