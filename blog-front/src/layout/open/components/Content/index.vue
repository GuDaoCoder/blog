<template>
  <router-view v-slot="{ Component, route }">
    <transition appear mode="out-in" name="fade">
      <div class="content-wrapper">
        <component
            :is="Component"
            :key="route.path"
        />
      </div>
    </transition>
  </router-view>
</template>

<script lang="ts" setup>

</script>

<style lang="scss" scoped>
.content-wrapper {
  padding: 20px 200px;
}
</style>