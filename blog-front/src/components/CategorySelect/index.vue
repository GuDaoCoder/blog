<template>
  <a-tree-select
      v-model="model"
      placeholder="全部"
      :field-names="{
                  key:'categoryId',
                  title:'categoryName'
                }"
      :data="categoryTreeData"
      selectable="leaf"
      :allow-search="true"
      :filter-tree-node="filterTreeNode"
      :allow-clear="true"
  ></a-tree-select>
</template>

<script setup lang="ts">
import {ref} from "vue";
import {treeCategory} from "@/api/category-manage";
import type {TreeNodeData} from "@arco-design/web-vue";
import {useVModel} from "@/utils/useVModel";

const props = defineProps({
  modelValue: {
    type: String,
    default: ""
  }
})

const emits = defineEmits(["update:modelValue"])

const categoryTreeData = ref<TreeCategoryVO[]>([])
const fetchCategoryTree = async () => {
  const {data} = await treeCategory({})
  categoryTreeData.value = data;
}

fetchCategoryTree();

const filterTreeNode = (searchValue: string, nodeData: TreeNodeData) => {
  return nodeData.title?.toLowerCase().includes(searchValue.toLowerCase()) || false;
}

const model = useVModel(props, "modelValue", emits)
</script>

<style scoped>

</style>