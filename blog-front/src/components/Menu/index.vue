<template>
  <a-menu
      class="menu"
      show-collapse-button
      level-indent.number="40"
      :collapsed="collapsed"
      @collapse="setCollapse"
      @menu-item-click="clickMenuItem"
  >
    <menu-item :data="menuData"/>
  </a-menu>
</template>

<script setup lang="ts">
import MenuItem from '@/components/MenuItem/index.vue'
import {reactive, computed} from 'vue'
import {useAppStore} from '@/store'
import {useRouter} from 'vue-router'
import type {Menu} from './type'

const appStore = useAppStore();
const router = useRouter();

const menuData = reactive<Array<Menu>>([{
  code: "admin-home",
  name: "主页",
  icon: "home"
}, {
  code: "admin-blog-manage",
  name: "博客管理",
  icon: "blog",
  children: [
    {
      code: "admin-category-manage",
      name: "分类管理"
    },
    {
      code: "admin-tag-manage",
      name: "标签管理"
    },
    {
      code: "admin-post-manage",
      name: "文章管理"
    }
  ]
}]);

/**
 * 菜单是否折叠
 */
const collapsed = computed(() => appStore.menuCollapse)

/**
 * 设置菜单是否折叠
 * @param val
 */
const setCollapse = (val: boolean) => {
  appStore.updateSettings({menuCollapse: val})
}

/**
 * 点击菜单项
 * @param code
 */
const clickMenuItem = (code: string) => {
  router.push({name: code})
}
</script>

<style scoped lang="scss">
.menu{
  width: 100%;
  height: 100%;
}
</style>