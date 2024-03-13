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
          <a-col :span="8">
            <a-form-item label="是否启用">
              <a-select v-model="searchCategoryForm.enabled" allow-clear placeholder="全部">
                <a-option value="true">是</a-option>
                <a-option value="false">否</a-option>
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
      <a-space direction="vertical" fill>
        <a-button type="primary" @click="handleSaveCategory">新增分类</a-button>
        <a-table :columns="tableColumns" :data="categoryTreeData" :loading="tableLoading" column-resizable
                 hide-expand-button-on-empty row-key="categoryId" stripe>
          <template #enabled="{record}">
            {{ $dict(Whether, record.enabled) }}
          </template>
          <template #operations="{ record }">
            <a-link @click="handleSaveChildCategory(record)">新增下级分类</a-link>
            <a-link @click="handleUpdateCategory(record)">编辑</a-link>
            <a-popconfirm v-if="!record.children || record.children.length ===0" content="确认删除?"
                          @ok="handleDeleteCategory(record)">
              <a-link>删除</a-link>
            </a-popconfirm>
          </template>
        </a-table>
      </a-space>
    </content-card>
  </Container>

  <!-- 创建或更新分类弹窗 -->
  <save-category :form-data="saveCategoryForm" :title="saveCategoryForm.categoryId ? '编辑':'新增' + '文章分类'"
                 :visible="saveFormVisible"
                 @cancel="handleCancelSave"/>
</template>
<script lang="ts" setup>
import ContentCard from '@/components/ContentCard/index.vue'
import SaveCategory from "@/views/admin/category-manage/components/save-category.vue";
import {onMounted, ref} from "vue";
import type {TableColumnData} from "@arco-design/web-vue";
import {Notification} from "@arco-design/web-vue";
import {deleteCategory, searchTree} from "@/api/admin/category-manage";
import SearchButtonGroup from "@/components/SearchButtonGroup/index.vue";
import {Whether} from "@/enums";

onMounted(() => {
  fetchTableData(searchCategoryForm.value)
})

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

/**
 * 列表列配置
 */
const tableColumns = ref<TableColumnData[]>([
  {
    title: "分类名称",
    dataIndex: "categoryName"
  },
  {
    title: "是否启用",
    dataIndex: "enabled",
    slotName: "enabled",
  },
  {
    title: "文章数量",
    dataIndex: "postCount"
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

const saveFormVisible = ref(false)
const saveCategoryForm = ref<SaveCategoryForm>({})

/**
 * 新增分类
 */
const handleSaveCategory = () => {
  saveCategoryForm.value = {
    parentCategoryId: 0,
    enabled: true
  }
  saveFormVisible.value = true
}

/**
 * 新增子级分类
 * @param value
 */
const handleSaveChildCategory = (value: CategoryTreeResponse) => {
  saveCategoryForm.value = {
    parentCategoryId: value.categoryId,
    parentCategoryName: value.categoryName,
    enabled: true
  }
  saveFormVisible.value = true
}

const handleUpdateCategory = (value: CategoryTreeResponse) => {
  saveCategoryForm.value = {...value}
  saveFormVisible.value = true
}

const handleDeleteCategory = async (value: CategoryTreeResponse) => {
  await deleteCategory(value.categoryId)
  Notification.success("删除成功");
  handleSearch()
}

const handleCancelSave = (reload: boolean) => {
  saveFormVisible.value = false
  if (reload) {
    // 创建成功关闭弹窗时需要触发搜索
    handleSearch()
  }
}

</script>

<style scoped>

</style>