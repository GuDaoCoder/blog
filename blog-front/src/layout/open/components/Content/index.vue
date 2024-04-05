<template>
  <router-view v-slot="{ Component, route }">
    <transition appear mode="out-in" name="fade">
      <div :key="route.path">
        <component :is="Component"/>
      </div>
    </transition>
  </router-view>
</template>

<script lang="ts" setup>
</script>

<style lang="scss" scoped>
</style>