<script lang="ts" setup>
import UserCard from "@/components/UserCard/index.vue"
import TagCloud from "@/components/TagCloud/index.vue"
import PostCarousel from "@/components/PostCarousel/index.vue"
import PostList from "@/components/PostList/index.vue"
</script>

<template>
  <div class="content-wrapper">
    <div class="card-wrapper">
      <user-card/>
      <tag-cloud/>
    </div>
    <div class="main-wrapper">
      <post-carousel/>
      <post-list/>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.content-wrapper {
  display: flex;
}

.card-wrapper {
  width: 250px;
}

.main-wrapper {
  flex: 1;
  margin-left: 20px;
}
</style>