<template>
  <Container>
    <content-card>
      <a-form :model="searchFormData" @submit="handleSearch">
        <a-row :gutter="16">
          <a-col :span="8">
            <a-form-item label="分类名称">
              <a-input v-model="searchFormData.categoryName"/>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="是否启用">
              <a-select allow-clear placeholder="全部" v-model="searchFormData.enabled">
                <a-option value="true">是</a-option>
                <a-option value="false">否</a-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <operations-group>
              <a-button type="primary" html-type="submit">查询</a-button>
              <a-button type="outline" @click="reset">重置</a-button>
            </operations-group>
          </a-col>
        </a-row>
      </a-form>
      <a-divider/>
      <a-space direction="vertical" fill>
        <a-button type="primary">新增分类</a-button>
        <a-table row-key="categoryId" :columns="tableColumns" :data="categoryTableData" stripe
                 hide-expand-button-on-empty column-resizable :loading="tableLoading">
          <template #operations="{ record }">
            <a-link @click="addCategory(record.categoryId)">新增下级分类</a-link>
            <a-link>修改</a-link>
            <a-link>删除</a-link>
          </template>
        </a-table>
      </a-space>
    </content-card>
  </Container>
</template>
<script setup lang="ts">
import ContentCard from '@/components/ContentCard/index.vue'
import OperationsGroup from "@/components/OperationsGroup/index.vue"
import {ref} from "vue";
import type {TableColumnData} from "@arco-design/web-vue";
import type {CategoryTreeRequestType} from "@/types/request-type"
import type {CategoryTreeResponseType} from "@/types/response-type";
import {categoryTree} from "@/api/category-manage";

const initForm = (): CategoryTreeRequestType => {
  return {
    categoryName: "",
    enabled: undefined
  }
}

const searchFormData = ref<CategoryTreeRequestType>(initForm())

const handleSearch = () => {
  fetchTableData(searchFormData.value)
}

const reset = () => {
  searchFormData.value = initForm();
}

const tableColumns = ref<TableColumnData[]>([
  {
    title: "分类名称",
    dataIndex: "categoryName"
  },
  {
    title: "是否启用",
    dataIndex: "enabled"
  },
  {
    title: "更新时间",
    dataIndex: "updateTime",
  },
  {
    title: "操作",
    dataIndex: "operations",
    slotName: 'operations',
    fixed: "right"
  }
])

const tableLoading = ref<boolean>(false)

const categoryTableData = ref<CategoryTreeResponseType[]>([])

const fetchTableData = async (params: CategoryTreeRequestType = {}) => {
  tableLoading.value = true
  try {
    const {data} = await categoryTree(params)
    categoryTableData.value = data
  } finally {
    tableLoading.value = false
  }
}

fetchTableData()

const addCategory = (parentId: string) => {
  alert(parentId)
}

</script>

<style scoped>

</style>