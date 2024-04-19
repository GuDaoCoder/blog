<template>
  <a-tree-select
      v-model="model"
      :allow-clear="true"
      :allow-search="true"
      :data="categoryTreeData"
      :field-names="{
                  key:'categoryId',
                  title:'categoryName'
                }"
      :filter-tree-node="filterTreeNode"
      placeholder="全部"
      selectable="leaf"
  ></a-tree-select>
</template>

<script lang="ts" setup>
import {onMounted, ref} from "vue";
import type {TreeNodeData} from "@arco-design/web-vue";
import {useVModel} from "@/utils/useVModel";
import categoryApi from "@/api/category/index";
import type {CategoryTreeResponse} from "@/api/category/types";

const props = defineProps({
  modelValue: {
    type: Number,
    default: ""
  }
})

const emits = defineEmits(["update:modelValue"])

const categoryTreeData = ref<CategoryTreeResponse[]>([])
const fetchCategoryTree = async () => {
  const {data} = await categoryApi.queryCategoryTree({})
  categoryTreeData.value = data;
}

onMounted(() => {
  fetchCategoryTree();
})

const filterTreeNode = (searchValue: string, nodeData: TreeNodeData) => {
  return nodeData.title?.toLowerCase().includes(searchValue.toLowerCase()) || false;
}

const model = useVModel(props, "modelValue", emits)
</script>

<style scoped>

</style>