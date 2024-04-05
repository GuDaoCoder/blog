<script lang="ts" setup>
import NavBar from "@/layout/open/components/NavBar/index.vue";
import Footer from "@/layout/open/components/Footer/index.vue"
import TagCard from "@/components/TagCard/index.vue";
import UserCard from "@/components/UserCard/index.vue";
import CategoryCard from "@/components/CategoryCard/index.vue";
import Content from "@/layout/open/components/Content/index.vue"
</script>

<template>
  <a-layout class="layout">
    <!-- Header -->
    <div class="layout-navbar">
      <NavBar/>
    </div>
    <a-layout>
      <a-layout class="layout-content">
        <a-layout-content class="content-wrapper">
          <div class="card-wrapper">
            <user-card/>
            <category-card/>
            <tag-card/>
          </div>
          <div class="main-wrapper">
            <Content/>
          </div>
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

.content-wrapper {
  padding: 20px 200px;
  display: flex;
}

.card-wrapper {
  width: 250px;
}

.card-wrapper > div:not(:first-child) {
  margin-top: 10px;
}

.main-wrapper {
  flex: 1;
  margin-left: 20px;
  background-color: #FFFFFF;
}
</style>