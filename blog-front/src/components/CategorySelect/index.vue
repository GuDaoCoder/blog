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
import {ref} from "vue";
import {searchTree} from "@/api/admin/category";
import type {TreeNodeData} from "@arco-design/web-vue";
import {useVModel} from "@/utils/useVModel";

const props = defineProps({
  modelValue: {
    type: Number,
    default: ""
  }
})

const emits = defineEmits(["update:modelValue"])

const categoryTreeData = ref<CategoryTreeResponse[]>([])
const fetchCategoryTree = async () => {
  const {data} = await searchTree({})
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