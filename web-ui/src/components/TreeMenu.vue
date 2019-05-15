<template>
  <div class="TreeMenu">
    <div class="label-wrapper" @click="toggleChildren(label)">
      <div :style="indent">
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


<style>

.TreeMenu {
  .label-wrapper {
    padding-bottom: 10px;
    margin-bottom: 10px;
    border-bottom: 1px solid #ccc;
    .has-children {
      cursor: pointer;
    }
  }
}

</style>


<script>
  import axios from 'axios';

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
