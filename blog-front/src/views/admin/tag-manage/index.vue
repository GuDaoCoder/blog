<template>
  <Container>
    <content-card>
      <a-form :model="searchFormData" @submit="handleSearch">
        <a-row :gutter="16">
          <a-col :span="8">
            <a-form-item label="标签名称">
              <a-input v-model="searchFormData.tagName"/>
            </a-form-item>
          </a-col>
          <a-col :span="8">
          </a-col>
        </a-row>
        <search-button-group>
          <a-button type="primary" html-type="submit">查询</a-button>
          <a-button type="outline" @click="reset">重置</a-button>
        </search-button-group>
      </a-form>
      <a-divider/>
      <a-space direction="vertical" fill>
        <a-button type="primary" @click="handleCreateTag">新增标签</a-button>
        <a-table row-key="tagId" :columns="tableColumns" :data="tagTableData" stripe column-resizable
                 :loading="tableLoading" :pagination="false">
          <template #operations="{ record }">
            <a-link @click="handleUpdateTag(record)">编辑</a-link>
            <a-popconfirm content="确认删除?" @ok="handleDeleteTag(record)">
              <a-link>删除</a-link>
            </a-popconfirm>
          </template>
        </a-table>

        <a-pagination v-model:current="page.pageNumber" v-model:page-size="page.pageSize" :total="page.total"
                      show-page-size show-total @change="handleChangePage" @page-size-change="handleChangePageSize"/>
      </a-space>
    </content-card>
  </Container>
</template>

<script setup lang="ts">
import ContentCard from '@/components/ContentCard/index.vue'
import {ref} from "vue";
import OperationsGroup from "@/components/OperationsGroup/index.vue";
import type {TableColumnData} from "@arco-design/web-vue";
import {pageTag} from "@/api/tag-manage";
import SearchButtonGroup from "@/components/SearchButtonGroup/index.vue";

const colors = ref({})

const initSearchForm = (): SearchTagForm => {
  return {
    tagName: ""
  }
}

const searchFormData = ref<SearchTagForm>(initSearchForm())

const handleSearch = () => {
  alert(colors.value)
  fetchTableData(searchFormData.value)
}

/**
 * 重置按钮
 */
const reset = () => {
  searchFormData.value = initSearchForm();
  fetchTableData(searchFormData.value)
}

const tableColumns = ref<TableColumnData[]>([
  {
    title: "标签名称",
    dataIndex: "tagName"
  },
  {
    title: "是否启用",
    dataIndex: "enable"
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

const page = ref<PageVO<PageTagVO>>({pageNumber: 1, pageSize: 10, total: 0})

const tableLoading = ref<boolean>(false)

const tagTableData = ref<PageTagVO[]>([])

const fetchTableData = async (form: SearchTagForm = {}) => {
  if (tableLoading.value) {
    return
  }
  tableLoading.value = true
  try {
    const {data} = await pageTag({
          pageNumber: page.value.pageNumber,
          pageSize: page.value.pageSize,
          ...form
        }
    )
    tagTableData.value = data.items || []
    page.value.pageNumber = data.pageNumber
    page.value.pageSize = data.pageSize
    page.value.total = data.total
  } finally {
    tableLoading.value = false
  }
}

fetchTableData()

const handleCreateTag = () => {

}

const handleUpdateTag = (value: PageTagVO) => {

}

const handleDeleteTag = (value: PageTagVO) => {

}

const handleChangePage = () => {
  fetchTableData(searchFormData.value)
}

const handleChangePageSize = () => {
  fetchTableData(searchFormData.value)
}


</script>

<style scoped>

</style>