<template>
  <a-space :class="center ? 'center':''">
    <slot/>
  </a-space>
</template>

<script setup lang="ts">
defineProps({
  center: {
    type: Boolean,
    default: false
  }
})
</script>

<style scoped lang="scss">
.center {
  margin: 0 auto;
}
</style>