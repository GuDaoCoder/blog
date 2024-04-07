<script lang="ts" setup>
import NavBar from "@/layout/open/components/NavBar/index.vue";
import Footer from "@/layout/open/components/Footer/index.vue"
import TagCard from "@/components/TagCard/index.vue";
import UserCard from "@/components/UserCard/index.vue";
import CategoryCard from "@/components/CategoryCard/index.vue";
import Content from "@/layout/open/components/Content/index.vue"
import AnchorCard from "@/components/AnchorCard/index.vue"
import {computed} from "vue";
import {useRoute} from "vue-router";

const route = useRoute();
const showAnchorCard = computed(() => {
  return route.name === "post"
})

</script>

<template>
  <a-layout class="layout">
    <!-- Header -->
    <div class="layout-navbar">
      <NavBar/>
    </div>
    <a-layout>
      <a-layout id="return-flag" class="layout-content">
        <a-layout-content class="content-wrapper">
          <div class="card-wrapper">
            <a-affix :offsetTop="80">
              <div class="card-items">
                <user-card/>
                <transition name="slide-up">
                  <anchor-card v-if="showAnchorCard"/>
                </transition>
                <category-card/>
                <tag-card/>
              </div>
            </a-affix>
          </div>
          <div class="main-wrapper">
            <Content/>
          </div>
        </a-layout-content>
      </a-layout>
      <Footer/>
    </a-layout>
  </a-layout>

  <a-back-top style="bottom: 70px;right: 40px">
    <svg-icon :size="60" class="back-top-button pointer" name="back-top"/>
  </a-back-top>
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
}

.card-wrapper {
  width: 250px;
  float: left;
  margin-right: 20px;
}

.card-items > div:not(:first-child) {
  margin-top: 10px;
}

.main-wrapper {
  overflow: hidden;
  background-color: #FFFFFF;
}

.back-top-button {
  transition: 0.3s;

  &:hover {
    transform: scale(1.3);
  }
}

.slide-up-enter-active, .slide-up-leave-active {
  transition: transform 0.5s ease;
}

.slide-up-enter, .slide-up-leave-to {
  transform: translateY(100%);
}

.slide-up-enter-active {
  animation: slideUp 0.5s ease forwards;
}

@keyframes slideUp {
  from {
    transform: translateY(100%);
  }
  to {
    transform: translateY(0);
  }
}
</style>