<template>
  <div class="wrapper">
    <a-space :class="center ? 'center':''">
      <slot/>
    </a-space>
  </div>
</template>

<script lang="ts" setup>
defineProps({
  center: {
    type: Boolean,
    default: false
  }
})
</script>

<style lang="scss" scoped>
.wrapper {
  padding: 10px 0;
}

.center {
  display: flex;
  justify-content: center
}
</style>