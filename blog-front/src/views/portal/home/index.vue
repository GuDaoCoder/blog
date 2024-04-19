<script lang="ts" setup>
import PostCarousel from "@/components/PostCarousel/index.vue"
import PostList from "@/components/PostList/index.vue"
import Pagination from "@/components/Pagination/index.vue"
import postApi from "@/api/post/index";
import {onMounted, ref} from "vue";
import type {PostResponse} from "@/api/post/types";

onMounted(() => {
  fetchPosts();
})

const pagination = ref<PaginationType>(
    {
      pageNumber: 1,
      pageSize: 10,
      total: 0
    })
const posts = ref<PostResponse[]>([])
const fetchPosts = async () => {
  const {data} = await postApi.portalQueryBlogPost({
    pageNumber: pagination.value.pageNumber,
    pageSize: pagination.value.pageSize,
  })
  posts.value = data.items || []
  pagination.value.pageNumber = data.pageNumber
  pagination.value.pageSize = data.pageSize
  pagination.value.total = data.total
}

const handleChangePage = (pageNumber: number) => {
  pagination.value.pageNumber = pageNumber
  fetchPosts()
}

const handleChangePageSize = (pageSize: number) => {
  pagination.value.pageSize = pageSize
  fetchPosts()
}
</script>

<template>
  <post-carousel/>
  <post-list :data="{items:posts}"/>
  <div class="pagination-wrapper">
    <Pagination :pagination="pagination" @change="handleChangePage" @page-size-change="handleChangePageSize"/>
  </div>
</template>

<style lang="scss" scoped>
.pagination-wrapper {
  padding: 10px 20px;
  text-align: right;
}
</style>