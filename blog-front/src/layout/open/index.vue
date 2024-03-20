<script lang="ts" setup>
import NavBar from "@/layout/open/components/NavBar/index.vue";
import Footer from "@/layout/open/components/Footer/index.vue"
import Content from "@/layout/open/components/Content/index.vue";
</script>

<template>
  <a-layout class="layout">
    <!-- Header -->
    <div class="layout-navbar">
      <NavBar/>
    </div>
    <a-layout>
      <a-layout class="layout-content">
        <a-layout-content>
          <Content/>
        </a-layout-content>
      </a-layout>
      <Footer/>
    </a-layout>
  </a-layout>
</template>

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

.layout-content {
  min-height: calc(100vh - 40px);
  overflow-y: hidden;
  background-color: rgb(242, 243, 245);
  padding-top: 60px;
}
</style>