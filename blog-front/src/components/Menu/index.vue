<template>
  <a-menu
      :style="{ width: '100%', height: '100%' }"
      show-collapse-button
  >
    <menu-item :data="menuData"/>
  </a-menu>
</template>

<script setup lang="ts">
import MenuItem from '@/components/MenuItem/index.vue'
import {reactive} from 'vue'
import type {Menu} from './type'

const menuData = reactive<Array<Menu>>([{
  code: "home",
  name: "主页",
  url: "/home",
  icon: "home"
}, {
  code: "blog-manage",
  name: "博客管理",
  url: "",
  icon: "blog",
  children: [
    {
      code: "category-manage",
      name: "分类管理",
      url: "/category-manage",
      icon: "",
      children:[{
        code: "category-manage1",
        name: "分类管理1",
        url: "/category-manage",
        icon: ""
      }]
    },
    {
      code: "post-manage",
      name: "文章管理",
      url: "/post-manage",
      icon: ""
    },
    {
      code: "tag-manage",
      name: "标签管理",
      url: "/tag-manage",
      icon: ""
    }
  ]
}]);
</script>

<style scoped>

</style>