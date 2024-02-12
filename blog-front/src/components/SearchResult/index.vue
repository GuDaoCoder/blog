<template>
  <a-space direction="vertical" fill>
    <operations-group>
      <slot name="toolbar"/>
    </operations-group>
    <slot name="table"/>
    <slot name="pagination"/>
  </a-space>
</template>

<script setup lang="ts">
import OperationsGroup from "@/components/OperationsGroup/index.vue";
</script>

<style scoped>

</style>