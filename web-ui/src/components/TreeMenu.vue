<template>
  <div class="TreeMenu">
    <div class="label-wrapper" @click="toggleChildren(label)">
      <div class="label" :style="indent">
        {{ label }}
      </div>

    </div>
    <TreeMenu
      v-if="showChildren"
      v-for="node in nodes"
      :nodes="node.children"
      :label="node.name"
      :depth="depth + 1"    >
  </TreeMenu>

  </div>
</template>


<style lang="scss" scoped>

.label:hover {
  color: $primary-color;
  cursor: pointer;
}

</style>


<script>

  export default {
    name: 'TreeMenu',
    props: [ 'nodes', 'label', 'depth','ids'],
    data() {
       return {
         showChildren: false,
       }
    },
    computed: {
    indent() {
      return { transform: `translate(${this.depth * 25}px)` }
    }
  },

  methods: {
    toggleChildren(label) {
       this.showChildren = !this.showChildren;
       // retrieve category id
       this.$router.push({ name: 'category', params: { id: this.ids.data[label].toString() }})
    }
  }
}
</script>
