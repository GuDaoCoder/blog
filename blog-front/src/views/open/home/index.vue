<script lang="ts" setup>
import PostCarousel from "@/components/PostCarousel/index.vue"
import PostList from "@/components/PostList/index.vue"
import {searchBlogPosts} from "@/api/blog/post";
import {onMounted, ref} from "vue";

onMounted(() => {
  fetchPosts();
})

const posts = ref<PostBlogResponse[]>([])
const fetchPosts = async () => {
  const {data} = await searchBlogPosts({pageNumber: 1, pageSize: 10})
  posts.value = data.items || []
}
</script>

<template>
  <div class="content-wrapper">
    <!--    <div class="card-wrapper">-->
    <!--      <user-card/>-->
    <!--      <tag-cloud/>-->
    <!--    </div>-->
    <div class="main-wrapper">
      <post-carousel/>
      <post-list :data="{items:posts}"/>
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
  background-color: #FFFFFF;
}
</style>