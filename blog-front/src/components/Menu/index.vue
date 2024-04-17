<template>
  <a-menu
      :collapsed="collapsed"
      class="menu"
      level-indent.number="40"
      show-collapse-button
      @collapse="setCollapse"
      @menu-item-click="clickMenuItem"
  >
    <menu-item :data="menuData"/>
  </a-menu>
</template>

<script lang="ts" setup>
import MenuItem from '@/components/MenuItem/index.vue'
import {computed, reactive} from 'vue'
import {useAppStore} from '@/store'
import {useRoute, useRouter} from 'vue-router'
import type {Menu} from './type'

const appStore = useAppStore();
const router = useRouter();
useRoute();
const menuData = reactive<Array<Menu>>([{
  code: "admin-home",
  title: "主页",
  icon: "home"
}, {
  code: "admin-portal-manage",
  title: "博客管理",
  icon: "portal",
  children: [
    {
      code: "admin-category",
      title: "分类管理"
    },
    {
      code: "admin-tag",
      title: "标签管理"
    },
    {
      code: "admin-post",
      title: "文章管理"
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

<style lang="scss" scoped>
.menu {
  width: 100%;
  height: 100%;
}
</style>