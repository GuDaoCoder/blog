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
        </a-row>
        <search-button-group>
          <a-button type="primary" html-type="submit">查询</a-button>
          <a-button type="outline" @click="reset">重置</a-button>
        </search-button-group>
      </a-form>
      <a-divider/>
      <search-result>
        <template #toolbar>
          <a-button type="primary" @click="handleCreateTag">新增标签</a-button>
        </template>

        <template #table>
          <a-table row-key="tagId" :columns="tableColumns" :data="tagTableData" stripe column-resizable
                   :loading="tableLoading" :pagination="false">
            <template #tagName="{record}">
              <a-tag :color="record.color">{{ record.tagName }}</a-tag>
            </template>
            <template #enable="{record}">
              {{ $dict(Whether, record.enable) }}
            </template>
            <template #operations="{ record }">
              <a-link @click="handleUpdateTag(record)">编辑</a-link>
              <a-popconfirm content="确认删除?" @ok="handleDeleteTag(record)">
                <a-link>删除</a-link>
              </a-popconfirm>
            </template>
          </a-table>
        </template>

        <template #pagination>
          <Pagination :pagination="pagination" @change="handleChangePage" @page-size-change="handleChangePageSize"/>
        </template>
      </search-result>
    </content-card>
  </Container>

  <save-tag :form-data="saveTagFormData" :visible="saveTagVisible" :title="saveTagFormData.tagId ? '编辑':'新增'+'标签'"
            @cancel="handleCancelSave"/>
</template>

<script setup lang="ts">
import Pagination from "@/components/Pagination/index.vue"
import ContentCard from '@/components/ContentCard/index.vue'
import {onMounted, ref} from "vue";
import type {TableColumnData} from "@arco-design/web-vue";
import {deleteTag, pageTag} from "@/api/tag-manage";
import SearchButtonGroup from "@/components/SearchButtonGroup/index.vue";
import SearchResult from "@/components/SearchResult/index.vue";
import SaveTag from "@/views/admin/tag-manage/components/save-tag.vue";
import {Notification} from "@arco-design/web-vue";
import {Whether} from "../../../enums";

const initSearchForm = (): SearchTagForm => {
  return {
    tagName: ""
  }
}

const searchFormData = ref<SearchTagForm>(initSearchForm())

const handleSearch = () => {
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
    dataIndex: "tagName",
    slotName: "tagName",
  },
  {
    title: "文章数量",
    dataIndex: "postCount",
  },
  {
    title: "是否启用",
    dataIndex: "enable",
    slotName: "enable",
  },
  {
    title: "更新时间",
    dataIndex: "updateTime",
  },
  {
    title: "操作",
    dataIndex: "operations",
    slotName: 'operations',
    fixed: "right",
    width: 120
  }
])

const pagination = ref<PaginationType>(
    {
      pageNumber: 1,
      pageSize: 10,
      total: 0
    })

const tableLoading = ref<boolean>(false)

const tagTableData = ref<PageTagVO[]>([])

const fetchTableData = async (form: SearchTagForm = {}) => {
  if (tableLoading.value) {
    return
  }
  tableLoading.value = true
  try {
    const {data} = await pageTag({
          pageNumber: pagination.value.pageNumber,
          pageSize: pagination.value.pageSize,
          ...form
        }
    )
    tagTableData.value = data.items || []
    pagination.value.pageNumber = data.pageNumber
    pagination.value.pageSize = data.pageSize
    pagination.value.total = data.total
  } finally {
    tableLoading.value = false
  }
}

onMounted(() => {
  fetchTableData()
})

const saveTagVisible = ref<boolean>(false)
const saveTagFormData = ref<SaveTagForm>({})

const handleCreateTag = () => {
  saveTagFormData.value = {
    color: "#000000",
    enable: true
  }
  saveTagVisible.value = true
}

const handleUpdateTag = (value: PageTagVO) => {
  saveTagFormData.value = {...value}
  saveTagVisible.value = true
}

const handleCancelSave = (reload: boolean) => {
  saveTagVisible.value = false
  if (reload) {
    handleSearch()
  }
}

const handleDeleteTag = async (value: PageTagVO) => {
  await deleteTag(value.tagId)
  Notification.success("删除成功");
  handleSearch()
}

const handleChangePage = (pageNumber: number) => {
  pagination.value.pageNumber = pageNumber
  fetchTableData(searchFormData.value)
}

const handleChangePageSize = (pageSize: number) => {
  pagination.value.pageSize = pageSize
  fetchTableData(searchFormData.value)
}

</script>

<style scoped>

</style>