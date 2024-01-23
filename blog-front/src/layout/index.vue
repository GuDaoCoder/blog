<template>
  <a-layout class="layout">
    <!-- Header -->
    <div class="layout-navbar">
      <NavBar/>
    </div>
    <a-layout>
      <a-layout>
        <!-- Sider -->
        <a-layout-sider class="layout-sider">
          <Menu/>
        </a-layout-sider>

        <a-layout class="layout-content">
          <!-- TabBar -->
          <TabBar/>

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
import NavBar from '@/layout/components/NavBar/index.vue'
import TabBar from '@/layout/components/TarBar/index.vue'
import Footer from '@/layout/components/Footer/index.vue'
import Content from '@/layout/components/Content/index.vue'
import Menu from '@/components/Menu/index.vue'
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
  background: aqua;
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

.layout-content {
  min-height: 100vh;
  padding-left: 200px;
  overflow-y: hidden;
  background-color: rgb(242, 243, 245);
  transition: padding 0.2s cubic-bezier(0.34, 0.69, 0.1, 1);
}
</style>