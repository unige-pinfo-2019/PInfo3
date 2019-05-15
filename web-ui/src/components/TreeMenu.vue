<template>
  <div class="TreeMenu">
    <div class="label-wrapper" @click="toggleChildren">
      <div :style="indent" :class="labelClasses">
        <i v-if="nodes"></i>
        <span> {{ label }} </span>
      </div>
    </div>
    <TreeMenu
      v-if="showChildren"
      v-for="node in nodes"
      :nodes="node.children"
      :label="node.name"
      :depth="depth + 1"
    >
  </TreeMenu>
  </div>
</template>


<style>

.Tree {
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
  export default {
    name: 'TreeMenu',
    props: [ 'nodes', 'label', 'depth' ],
    data() {
       return {
         showChildren: false
       }
    },
    computed: {
    labelClasses() {
      return { 'has-children': this.nodes }
    },
    indent() {
      return { transform: `translate(${this.depth * 50}px)` }
    }
  },
  methods: {
    toggleChildren() {
       this.showChildren = !this.showChildren;
    }
  }
}
</script>
