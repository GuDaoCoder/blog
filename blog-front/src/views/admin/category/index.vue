<template>
  <Container>
    <content-card>
      <a-form :model="queryCategoryForm" @submit="handleSearch">
        <a-row :gutter="16">
          <a-col :span="8">
            <a-form-item label="分类名称">
              <a-input v-model="queryCategoryForm.categoryName"/>
            </a-form-item>
          </a-col>
        </a-row>
        <search-button-group>
          <a-button html-type="submit" type="primary">查询</a-button>
          <a-button type="outline" @click="reset">重置</a-button>
        </search-button-group>
      </a-form>

      <a-divider/>

      <search-result>
        <template #table>
          <a-table :columns="tableColumns" :data="categoryTreeData" :loading="tableLoading" :pagination="false"
                   column-resizable
                   hide-expand-button-on-empty row-key="categoryId" stripe>
            <template #postCount="{record}">
              {{ record.children && record.children.length > 0 ? "-" : record.postCount }}
            </template>
          </a-table>
        </template>
      </search-result>
    </content-card>
  </Container>
</template>
<script lang="ts" setup>
import ContentCard from '@/components/ContentCard/index.vue'
import {onMounted, ref} from "vue";
import type {TableColumnData} from "@arco-design/web-vue";
import SearchButtonGroup from "@/components/SearchButtonGroup/index.vue";
import SearchResult from "@/components/SearchResult/index.vue";
import categoryApi from "@/api/category/index"
import type {CategoryTreeResponse, QueryCategoryTreeRequest} from "@/api/category/types";

const initQueryForm = (): QueryCategoryTreeRequest => {
  return {
    categoryName: ""
  }
}

const queryCategoryForm = ref<QueryCategoryTreeRequest>(initQueryForm())

/**
 * 查询按钮
 */
const handleSearch = () => {
  fetchTableData()
}

/**
 * 重置按钮
 */
const reset = () => {
  queryCategoryForm.value = initQueryForm();
  fetchTableData()
}

/**
 * 列表列配置
 */
const tableColumns = ref<TableColumnData[]>([
  {
    title: "分类名称",
    dataIndex: "categoryName"
  },
  {
    title: "文章数量",
    dataIndex: "postCount",
    slotName: "postCount"
  },
  {
    title: "更新时间",
    dataIndex: "updateTime",
  }
])

onMounted(() => {
  fetchTableData()
})

const tableLoading = ref<boolean>(false)
const categoryTreeData = ref<CategoryTreeResponse[]>([])

/**
 * 查询文章分类列表
 * @param form
 */
const fetchTableData = async () => {
  tableLoading.value = true
  try {
    const {data} = await categoryApi.queryCategoryTree({...queryCategoryForm.value})
    categoryTreeData.value = data
  } finally {
    tableLoading.value = false
  }
}

</script>

<style scoped>

</style>