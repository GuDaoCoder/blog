<template>
  <a-modal v-model:visible="visible">

  </a-modal>
</template>

<script setup lang="ts">
defineProps({
  visible:{
    type:Boolean,
    required: true
  },
  data:{

  }
})
</script>

<style scoped>

</style>