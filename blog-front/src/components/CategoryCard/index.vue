<script lang="ts" setup>
import {onMounted, ref} from "vue";
import categoryApi from "@/api/category/index";
import type {CategoryTreeResponse} from "@/api/category/types";

onMounted(() => {
  fetchCategoryTree()
})

const categoryTreeData = ref<CategoryTreeResponse[]>([])
const fetchCategoryTree = async () => {
  const {data} = await categoryApi.portalQueryCategoryTree({})
  categoryTreeData.value = data;
}
</script>

<template>
  <a-card :bordered="false" hoverable title="文章分类">
    <a-tree :data="categoryTreeData " :field-names="{key:'categoryId', title:'categoryName' }" show-line/>
  </a-card>
</template>

<style lang="scss" scoped>

</style>