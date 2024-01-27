<template>
  <a-layout class="layout">
    <!-- Header -->
    <div class="layout-navbar">
      <NavBar/>
    </div>
    <a-layout>
      <a-layout>
        <!-- Sider -->
        <a-layout-sider class="layout-sider" :width="menuWidth">
          <div class="menu-wrapper">
            <Menu/>
          </div>
        </a-layout-sider>

        <a-layout class="layout-content" :style="contentStyle">
          <!-- TabBar -->
          <!--          <TabBar/>-->
          <!-- Content -->
          <a-layout-content>
            <Content/>
          </a-layout-content>

          <!-- Footer -->
          <Footer/>
        </a-layout>
      </a-layout>
    </a-layout>
  </a-layout>
</template>

<script setup lang="ts">
import NavBar from '@/layout/admin/components/NavBar/index.vue'
import Footer from '@/layout/admin/components/Footer/index.vue'
import Content from '@/layout/admin/components/Content/index.vue'
import Menu from '@/components/Menu/index.vue'

import {computed} from 'vue'
import {useAppStore} from '@/store'

const appStore = useAppStore()

/**
 * 菜单栏宽度
 */
const menuWidth = computed(() => {
  return appStore.menuCollapse ? 48 : appStore.menuWidth;
});

/**
 * 内容区样式
 */
const contentStyle = computed(() => {
  const paddingLeft = {paddingLeft: `${menuWidth.value}px`}
  // todo：适配tabBar
  const paddingTop = {paddingTop: '60px'}
  return {...paddingLeft, ...paddingTop}
})
</script>

<style lang="scss" scoped>

.layout {
  width: 100%;
  height: 100%;
}

.layout-navbar {
  position: fixed;
  top: 0;
  left: 0;
  z-index: 100;
  width: 100%;
  height: 60px;
  background-color: #FFFFFF;
}

.layout-sider {
  position: fixed;
  top: 0;
  left: 0;
  z-index: 99;
  height: 100%;
  padding-top: 60px;
  transition: all 0.2s cubic-bezier(0.34, 0.69, 0.1, 1);

  &::after {
    position: absolute;
    top: 0;
    right: -1px;
    display: block;
    width: 1px;
    height: 100%;
    background-color: rgb(229, 230, 235);
    content: '';
  }

  > :deep(.arco-layout-sider-children) {
    overflow-y: hidden;
  }
}

.menu-wrapper {
  height: 100%;
  overflow: auto;
  overflow-x: hidden;

  :deep(.arco-menu) {
    ::-webkit-scrollbar {
      width: 12px;
      height: 4px;
    }

    ::-webkit-scrollbar-thumb {
      border: 4px solid transparent;
      background-clip: padding-box;
      border-radius: 7px;
      background-color: rgb(201, 205, 212);
    }

    ::-webkit-scrollbar-thumb:hover {
      background-color: rgb(229, 230, 235);
    }
  }
}


.layout-content {
  min-height: 100vh;
  overflow-y: hidden;
  background-color: rgb(242, 243, 245);
  transition: padding 0.2s cubic-bezier(0.34, 0.69, 0.1, 1);
}
</style>