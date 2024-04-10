<template>
  <Container>
    <content-card>
      <a-form :model="searchCategoryForm" @submit="handleSearch">
        <a-row :gutter="16">
          <a-col :span="8">
            <a-form-item label="分类名称">
              <a-input v-model="searchCategoryForm.categoryName"/>
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
import {searchTree} from "@/api/admin/category";
import SearchButtonGroup from "@/components/SearchButtonGroup/index.vue";
import SearchResult from "@/components/SearchResult/index.vue";

const initSearchForm = (): SearchCategoryForm => {
  return {
    categoryName: "",
    enabled: undefined
  }
}

const searchCategoryForm = ref<SearchCategoryForm>(initSearchForm())

/**
 * 查询按钮
 */
const handleSearch = () => {
  fetchTableData(searchCategoryForm.value)
}

/**
 * 重置按钮
 */
const reset = () => {
  searchCategoryForm.value = initSearchForm();
  fetchTableData(searchCategoryForm.value)
}

onMounted(() => {
  fetchTableData(searchCategoryForm.value)
})

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

const tableLoading = ref<boolean>(false)
const categoryTreeData = ref<CategoryTreeResponse[]>([])

/**
 * 查询文章分类列表
 * @param form
 */
const fetchTableData = async (form: SearchCategoryForm) => {
  tableLoading.value = true
  try {
    const {data} = await searchTree(form as CreateCategoryRequest)
    categoryTreeData.value = data
  } finally {
    tableLoading.value = false
  }
}

</script>

<style scoped>

</style>