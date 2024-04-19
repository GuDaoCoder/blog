<template>
  <Container>
    <content-card>
      <a-form :model="queryForm" @submit="handleSearch">
        <a-row :gutter="16">
          <a-col :span="8">
            <a-form-item label="标签名称">
              <a-input v-model="queryForm.tagName"/>
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
          <a-table :columns="tableColumns" :data="tagTableData" :loading="tableLoading" :pagination="false"
                   column-resizable
                   row-key="tagId" stripe>
            <template #tagName="{record}">
              <a-tag :color="record.color">{{ record.tagName }}</a-tag>
            </template>
          </a-table>
        </template>

        <template #pagination>
          <Pagination :pagination="pagination" @change="handleChangePage" @page-size-change="handleChangePageSize"/>
        </template>
      </search-result>
    </content-card>
  </Container>
</template>

<script lang="ts" setup>
import Pagination from "@/components/Pagination/index.vue"
import ContentCard from '@/components/ContentCard/index.vue'
import {onMounted, ref} from "vue";
import type {TableColumnData} from "@arco-design/web-vue";
import SearchButtonGroup from "@/components/SearchButtonGroup/index.vue";
import SearchResult from "@/components/SearchResult/index.vue";
import tagApi from "@/api/tag/index"
import type {SearchTagForm, TagDetailResponse} from "@/api/tag/types";

const initQueryForm = (): SearchTagForm => {
  return {
    tagName: ""
  }
}

const queryForm = ref<SearchTagForm>(initQueryForm())

const handleSearch = () => {
  fetchTableData()
}

/**
 * 重置按钮
 */
const reset = () => {
  queryForm.value = initQueryForm();
  fetchTableData()
}

const tableColumns = ref<TableColumnData[]>([
  {
    title: "标签名称",
    dataIndex: "tagName",
    slotName: "tagName",
  },
  {
    title: "文章数量",
    dataIndex: "postCount",
  },
  {
    title: "更新时间",
    dataIndex: "updateTime",
  }
])

const pagination = ref<PaginationType>(
    {
      pageNumber: 1,
      pageSize: 10,
      total: 0
    })

onMounted(() => {
  fetchTableData()
})

const tableLoading = ref<boolean>(false)

const tagTableData = ref<TagDetailResponse[]>([])

const fetchTableData = async () => {
  if (tableLoading.value) {
    return
  }
  tableLoading.value = true
  try {
    let params = {
      pageNumber: pagination.value.pageNumber,
      pageSize: pagination.value.pageSize,
      ...queryForm.value
    }
    const {data} = await tagApi.searchTag(params)
    tagTableData.value = data.items || []
    pagination.value.pageNumber = data.pageNumber
    pagination.value.pageSize = data.pageSize
    pagination.value.total = data.total
  } finally {
    tableLoading.value = false
  }
}

const handleChangePage = (pageNumber: number) => {
  pagination.value.pageNumber = pageNumber
  fetchTableData()
}

const handleChangePageSize = (pageSize: number) => {
  pagination.value.pageSize = pageSize
  fetchTableData()
}

</script>

<style scoped>

</style>