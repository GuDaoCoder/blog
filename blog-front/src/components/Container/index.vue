<template>
  <div class="container">
    <slot/>
  </div>
</template>

<script setup lang="ts">
import {useRoute} from "vue-router";

const route = useRoute()
console.log(route.matched);
</script>

<style scoped lang="scss">
.container {
  padding: 0 20px 20px 20px;
}
</style>