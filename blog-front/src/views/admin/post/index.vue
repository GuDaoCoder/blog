<template>
  <Container>
    <content-card>
      <a-form :model="queryForm" @submit="handleSearch">
        <a-row :gutter="16">
          <a-col :span="8">
            <a-form-item label="文章标题">
              <a-input v-model="queryForm.title"/>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="状态">
              <a-select v-model="queryForm.status" placeholder="全部">
                <a-option v-for="[key,value] in Object.entries(PostStatus)" :key="key" :value="key">{{ value }}
                </a-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="文章分类">
              <category-select v-model="queryForm.categoryId"/>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="置顶">
              <a-select v-model="queryForm.top" placeholder="全部">
                <a-option v-for="[key,value] in Object.entries(Whether)" :key="key" :value="key">{{ value }}</a-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="开启评论">
              <a-select v-model="queryForm.enableComment" placeholder="全部">
                <a-option v-for="[key,value] in Object.entries(Whether)" :key="key" :value="key">{{ value }}</a-option>
              </a-select>
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
        <template #toolbar>
          <a-button type="primary" @click="handleSyncPosts">同步</a-button>
        </template>
        <template #table>
          <a-table :columns="tableColumns" :data="postTableData" :loading="tableLoading" :pagination="false"
                   :scroll="scroll"
                   column-resizable row-key="tagId" stripe>
            <template #coverPictureUrl="{ record }">
              <a-image v-if="record.coverPictureUrl" :src="record.coverPictureUrl" height="100" show-loader
                       width="200"/>
            </template>
            <template #tags="{ record }">
              <div class="tag-wrapper">
                <a-tag v-for="item in record.tags" :key="item.tagName" :color="item.color" style="margin: 4px">
                  {{ item.tagName }}
                </a-tag>
              </div>
            </template>
            <template #status="{record}">
              {{ useDict(PostStatus, record.status) }}
            </template>
            <template #top="{record}">
              {{ useDict(Whether, record.top) }}
            </template>
            <template #enableComment="{record}">
              {{ useDict(Whether, record.enableComment) }}
            </template>
            <template #publishTime="{record}">{{ canPublish(record) ? "" : record.publishTime }}</template>
            <template #removeTime="{record}">{{ canRemove(record) ? "" : record.removeTime }}</template>
            <template #operations="{ record }">
              <!--              <a-link @click="handlePreview(record)">预览</a-link>-->
              <a-link @click="handleSetCoverPicture(record)">设置封面</a-link>
              <a-link v-if="canPublish(record)" @click="handlePublishPost(record)">发布</a-link>
              <a-link v-if="canRemove(record)" @click="handleRemovePost(record)">下架</a-link>
            </template>
          </a-table>
        </template>

        <template #pagination>
          <a-pagination v-model:current="pagination.pageNumber" v-model:page-size="pagination.pageSize"
                        :total="pagination.total"
                        show-page-size show-total @change="handleChangePage"
                        @page-size-change="handleChangePageSize"/>
        </template>
      </search-result>
    </content-card>
  </Container>

  <!-- 上传封面图片 -->
  <upload-cover-picture :post-id="uploadCoverPicturePostId" :visible="uploadCoverPictureVisible"
                        @cancel="handleCancelUploadCoverPicture"/>
</template>

<script lang="ts" setup>
import ContentCard from "@/components/ContentCard/index.vue";
import SearchButtonGroup from "@/components/SearchButtonGroup/index.vue"
import SearchResult from "@/components/SearchResult/index.vue"
import CategorySelect from "@/components/CategorySelect/index.vue";
import UploadCoverPicture from "./components/UploadCoverPicture/index.vue"
import {onMounted, ref} from "vue";
import {PostStatus, Whether} from "@/enums";
import type {TableColumnData} from "@arco-design/web-vue";
import {Message} from '@arco-design/web-vue';
import {useDict} from "@/utils/dict"
import postApi from "@/api/post/index"
import type {PostResponse, QueryPostForm} from "@/api/post/types";


const initQueryForm = (): QueryPostForm => {
  return {
    title: "",
    status: "",
    categoryId: undefined,
    top: undefined,
    enableComment: undefined
  }
}

const queryForm = ref<QueryPostForm>(initQueryForm())

const handleSearch = () => {
  fetchTableData()
}

const reset = () => {
  queryForm.value = initQueryForm();
  fetchTableData()
}

const tableColumns = ref<TableColumnData[]>([
  {
    title: "封面",
    dataIndex: "coverPictureUrl",
    slotName: "coverPictureUrl",
    width: 220
  },
  {
    title: "文章标题",
    dataIndex: "title",
    ellipsis: true,
    tooltip: {position: 'left'},
    width: 200
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
    width: 200
  },
  {
    title: "分类",
    dataIndex: "categoryName",
    width: 120
  },
  {
    title: "摘要",
    dataIndex: "summary",
    ellipsis: true,
    tooltip: {position: 'left'},
  },
  {
    title: "是否置顶",
    dataIndex: "top",
    slotName: "top",
    width: 100
  },
  {
    title: "发布时间",
    dataIndex: "publishTime",
    slotName: "publishTime",
    width: 180
  },
  {
    title: "下架时间",
    dataIndex: "removeTime",
    slotName: "removeTime",
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
    width: 220
  }
])

const scroll = {
  x: 1800
}

onMounted(() => {
  fetchTableData()
})

const pagination = ref<PaginationType>({pageNumber: 1, pageSize: 10, total: 0})

const postTableData = ref<PostResponse[]>([])

const tableLoading = ref<boolean>(false)

const fetchTableData = async () => {
  if (tableLoading.value) {
    return
  }
  tableLoading.value = true
  try {
    const {data} = await postApi.queryPost({
      pageNumber: pagination.value.pageNumber,
      pageSize: pagination.value.pageSize,
      ...queryForm.value
    })
    postTableData.value = data.items || []
    pagination.value.pageNumber = data.pageNumber
    pagination.value.pageSize = data.pageSize
    pagination.value.total = data.total
  } finally {
    tableLoading.value = false
  }
}

const handleSyncPosts = async () => {
  try {
    tableLoading.value = true
    await postApi.syncPost()
    Message.success("同步成功")
    handleSearch()
  } finally {
    if (tableLoading.value) {
      tableLoading.value = false
    }
  }

}

const handlePreview = (record: PostResponse) => {

}
const handlePublishPost = async (record: PostResponse) => {
  await postApi.publishPost(record.postId)
  handleSearch()
}

const handleRemovePost = async (record: PostResponse) => {
  await postApi.removePost(record.postId)
  handleSearch()
}

const handleChangePage = () => {
  fetchTableData()
}

const handleChangePageSize = () => {
  fetchTableData()
}

const canPublish = (record: PostResponse): boolean => {
  return record.status === "DRAFT" || record.status === "REMOVED";
}

const canRemove = (record: PostResponse): boolean => {
  return record.status === "PUBLISHED";
}

const uploadCoverPictureVisible = ref<boolean>(false)
const uploadCoverPicturePostId = ref()
const handleSetCoverPicture = async (record: PostResponse) => {
  uploadCoverPictureVisible.value = true
  uploadCoverPicturePostId.value = record.postId
}

const handleCancelUploadCoverPicture = () => {
  uploadCoverPictureVisible.value = false
  fetchTableData()
}
</script>

<style lang="scss" scoped>
.tag-wrapper {
  display: flex;
  flex-wrap: wrap;
}
</style>