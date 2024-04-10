<script lang="ts" setup>
import {onMounted, ref} from "vue";
import {blogSearchCategoryTree} from "@/api/blog/category";

const categoryTreeData = ref<CategoryTreeResponse[]>([])
const fetchCategoryTree = async () => {
  const {data} = await blogSearchCategoryTree({})
  categoryTreeData.value = data;
}

onMounted(() => {
  fetchCategoryTree()
})
</script>

<template>
  <a-card :bordered="false" hoverable title="文章分类">
    <a-tree :data="categoryTreeData " :field-names="{key:'categoryId', title:'categoryName' }" show-line/>
  </a-card>
</template>

<style lang="scss" scoped>

</style>