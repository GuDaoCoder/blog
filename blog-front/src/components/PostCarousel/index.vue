<script lang="ts" setup>
import {onMounted, ref} from "vue";
import postApi from "@/api/post/index";
import {useRouter} from "vue-router";
import type {PostResponse} from "@/api/post/types";

const posts = ref<PostResponse[]>([])

onMounted(() => {
  fetchPosts();
})

const fetchPosts = async () => {
  const {data} = await postApi.portalQueryBlogPost({pageNumber: 1, pageSize: 5})
  posts.value = data.items || []
}

const router = useRouter()
const toPost = (post: PostResponse) => {
  router.push({path: "/post", query: {postId: post.postId}})
}
</script>

<template>
  <a-carousel
      :autoPlay="true"
      animation-name="card"
      class="post-carousel"
      indicator-position="outer"
  >
    <a-carousel-item v-for="post in posts" style="width: 60%">
      <a-image
          :preview="false"
          :src="post.coverPictureUrl"
          class="pointer"
          fit="cover"
          height="100%" width="100%" @click="toPost(post)"/>
    </a-carousel-item>
  </a-carousel>
</template>

<style lang="scss" scoped>
.post-carousel {
  width: 100%;
  height: 300px;
}
</style>