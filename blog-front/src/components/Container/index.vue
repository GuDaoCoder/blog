<template>
  <div class="container">
    <div>
      <a-breadcrumb class="container-breadcrumb">
        <a-breadcrumb-item>
          <svg-icon name="apps"/>
        </a-breadcrumb-item>
        <a-breadcrumb-item v-for="item in items" :key="item">
          {{ item }}
        </a-breadcrumb-item>
      </a-breadcrumb>
    </div>
    <div>
      <slot/>
    </div>
  </div>
</template>

<script setup lang="ts">
import {useRoute} from "vue-router";
import {computed} from "vue";

const route = useRoute()
const items = computed(() => {
  return [...new Set(route.matched.map(obj => obj.meta.title).filter(obj => obj !== undefined))];
})

</script>

<style scoped lang="scss">
.container {
  padding: 0 20px 20px 20px;
}

.container-breadcrumb {
  margin: 16px 0;

  :deep(.arco-breadcrumb-item) {
    color: rgb(134, 144, 156);

    &:last-child {
      color: rgb(78, 89, 105);
    }
  }
}
</style>