<template>
  <Container>
    <content-card>
      <a-form :model="searchFormData" @submit="handleSearch">
        <a-row :gutter="16">
          <a-col :span="8">
            <a-form-item label="文章标题">
              <a-input v-model="searchFormData.title"/>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="状态">
              <a-select v-model="searchFormData.status" placeholder="全部">
                <a-option v-for="[key,value] in Object.entries(PostStatus)" :key="key" :value="key">{{ value }}
                </a-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="文章分类">
              <a-tree-select
                  v-model="searchFormData.categoryId"
                  :data="categoryTreeData"
                  placeholder="全部"
              ></a-tree-select>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="加密">
              <a-select v-model="searchFormData.encrypt" placeholder="全部">
                <a-option v-for="[key,value] in Object.entries(Whether)" :key="key" :value="key">{{ value }}</a-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="置顶">
              <a-select v-model="searchFormData.top" placeholder="全部">
                <a-option v-for="[key,value] in Object.entries(Whether)" :key="key" :value="key">{{ value }}</a-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="开启评论">
              <a-select v-model="searchFormData.enableComment" placeholder="全部">
                <a-option v-for="[key,value] in Object.entries(Whether)" :key="key" :value="key">{{ value }}</a-option>
              </a-select>
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
<!--        <template #toolbar>-->
<!--        </template>-->

        <template #table>
          <a-table row-key="tagId" :columns="tableColumns" :data="postTableData" stripe column-resizable
                   :loading="tableLoading" :pagination="false" :scroll="scroll">
            <template #coverPictureUrl="{ record }">
              <a-image v-if="record.coverPictureUrl" :src="record.coverPictureUrl" show-loader width="100"
                       height="100"/>
            </template>
            <template #tags="{ record }">
              <a-space>
                <a-tag v-for="item in record.tags" :key="item.tagName" :color="item.color">{{ item.tagName }}</a-tag>
              </a-space>
            </template>
            <template #status="{record}">
             {{ PostStatus[record.status]}}
            </template>
            <template #top="{record}">
              {{ Whether[record.top]}}
            </template>
            <template #enableComment="{record}">
              {{ Whether[record.enableComment]}}
            </template>
            <template #operations="{ record }">
              <a-link @click="handleUpdatePost(record)">编辑</a-link>
              <a-link @click="handleUpdatePost(record)">发布</a-link>
              <a-popconfirm content="确认删除?" @ok="handleDeletePost(record)">
                <a-link>移到回收站</a-link>
              </a-popconfirm>
            </template>
          </a-table>
        </template>

        <template #pagination>
          <a-pagination v-model:current="page.pageNumber" v-model:page-size="page.pageSize" :total="page.total"
                        show-page-size show-total @change="handleChangePage" @page-size-change="handleChangePageSize"/>
        </template>
      </search-result>
    </content-card>
  </Container>
</template>

<script setup lang="ts">
import ContentCard from "@/components/ContentCard/index.vue";
import SearchButtonGroup from "@/components/SearchButtonGroup/index.vue"
import SearchResult from "@/components/SearchResult/index.vue"
import {ref} from "vue";
import {PostStatus, Whether} from "@/enums";
import type {TableColumnData} from "@arco-design/web-vue";
import {pagePost} from "@/api/post-manage";

const initSearchForm = (): SearchPostForm => {
  return {
    title: "",
    status: "",
    categoryId: "",
    encrypt: undefined,
    top: undefined,
    enableComment: undefined
  }
}

const searchFormData = ref<SearchPostForm>(initSearchForm())

const categoryTreeData = ref([])

const handleSearch = () => {

}

const reset = () => {
  initSearchForm();
}

const tableColumns = ref<TableColumnData[]>([
  {
    title: "封面",
    dataIndex: "coverPictureUrl",
    slotName: "coverPictureUrl",
    width: 120
  },
  {
    title: "文章标题",
    dataIndex: "title",
    width: 200
  },
  {
    title: "摘要",
    dataIndex: "summary"
  },
  {
    title: "状态",
    dataIndex: "status",
    slotName: "status",
    width: 120
  },
  {
    title: "标签",
    dataIndex: "tags",
    slotName: 'tags',
  },
  {
    title: "分类",
    dataIndex: "categoryName",
    width: 120
  },
  {
    title: "是否置顶",
    dataIndex: "top",
    slotName: "top",
    width: 100
  },
  {
    title: "是否加密",
    dataIndex: "enableComment",
    slotName: "enableComment",
    width: 100
  },
  {
    title: "发布时间",
    dataIndex: "publishTime",
    width: 180
  },
  {
    title: "更新时间",
    dataIndex: "updateTime",
    width: 180
  },
  {
    title: "操作",
    dataIndex: "operations",
    slotName: 'operations',
    fixed: "right",
    width: 200
  }
])

const scroll = {
  x: 1800
}

const page = ref<PageVO<PageTagVO>>({pageNumber: 1, pageSize: 10, total: 0})

const postTableData = ref<PagePostVO[]>([])

const tableLoading = ref(false)

const fetchTableData = async (form: SearchPostForm = {}) => {
  if (tableLoading.value) {
    return
  }
  tableLoading.value = true
  try {
    const {data} = await pagePost({
      pageNumber: page.value.pageNumber,
      pageSize: page.value.pageSize,
      ...form
    })
    postTableData.value = data.items || []
    page.value.pageNumber = data.pageNumber
    page.value.pageSize = data.pageSize
    page.value.total = data.total
  } finally {
    tableLoading.value = false
  }
}

fetchTableData()

const handleUpdatePost = (value: PagePostVO) => {

}

const handleDeletePost = (value: PagePostVO) => {

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