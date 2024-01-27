<template>
  <template v-for="item in data" :key="item.code">
    <a-menu-item :key="item.code" v-if="!item.children">
      <template #icon v-if="item.icon">
        <svg-icon :name="item.icon"/>
      </template>
      {{ item.title }}
    </a-menu-item>
    <a-sub-menu :key="item.code" v-if="item.children">
      <template #icon v-if="item.icon">
        <svg-icon :name="item.icon"/>
      </template>
      <template #title> {{ item.title }}</template>
      <menu-item :data="item.children"/>
    </a-sub-menu>
  </template>
</template>

<script setup lang="ts">
import MenuItem from '@/components/MenuItem/index.vue'
import type {Menu} from "@/components/Menu/type";

defineProps<{
  data: Array<Menu>
}>();
</script>

<style scoped>

</style>