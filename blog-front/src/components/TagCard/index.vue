<script lang="ts" setup>

import {onMounted, ref} from "vue";
import tagApi from "@/api/tag/index";
import type {TagResponse} from "@/api/tag/types";

onMounted(() => {
  fetchAllTags()
})

const tags = ref<TagResponse[]>([])
const fetchAllTags = async () => {

  const {data} = await tagApi.portalSearchTag({
        pageNumber: 1,
        pageSize: 9999
      }
  )
  tags.value = data.items || []
}
</script>

<template>
  <a-card :bordered="false" hoverable title="标签">
    <a-tag v-for="tag in tags" :key="tag.tagId" :color="tag.color" class="tag pointer">{{ tag.tagName }}</a-tag>
  </a-card>
</template>

<style lang="scss" scoped>
.tag {
  margin: 5px 0 0 5px;
}
</style>