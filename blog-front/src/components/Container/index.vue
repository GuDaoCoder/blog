<template>
  <div class="container">
    <a-breadcrumb class="container-breadcrumb">
      <a-breadcrumb-item>
        <svg-icon name="apps"/>
      </a-breadcrumb-item>
      <a-breadcrumb-item v-for="item in items" :key="item">
        {{ item }}
      </a-breadcrumb-item>
    </a-breadcrumb>
    <div class="content">
      <slot/>
    </div>
  </div>
</template>

<script setup lang="ts">
import {useRoute} from "vue-router";
import {computed} from "vue";

const route = useRoute()
const items = computed(() => {
  return [...new Set(route.matched.filter(obj => !obj.meta.hideMenu && obj.meta.title).map(obj => obj.meta.title))];
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

.content {
  border: none;
  border-radius: 4px;
  background: #FFFFFF;
}
</style>