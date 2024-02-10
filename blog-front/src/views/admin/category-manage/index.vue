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
        <a-button type="primary" @click="handleSaveCategory">新增分类</a-button>
        <a-table row-key="categoryId" :columns="tableColumns" :data="categoryTableData" stripe
                 hide-expand-button-on-empty column-resizable :loading="tableLoading">
          <template #operations="{ record }">
            <a-link @click="handleSaveChildCategory(record)">新增下级分类</a-link>
            <a-link @click="handleUpdateCategory(record)">编辑</a-link>
            <a-popconfirm content="确认删除?" @ok="handleDeleteCategory(record)"
                          v-if="!record.children || record.children.length ===0">
              <a-link>删除</a-link>
            </a-popconfirm>
          </template>
        </a-table>
      </a-space>
    </content-card>
  </Container>

  <!-- 创建或更新分类弹窗 -->
  <save-category :visible="saveFormVisible" :form-data="saveCategoryFormData"
                 :title="saveCategoryFormData.categoryId ? '编辑':'新增' + '文章分类'"
                 @cancel="handleCancelSave"/>
</template>
<script setup lang="ts">
import ContentCard from '@/components/ContentCard/index.vue'
import OperationsGroup from "@/components/OperationsGroup/index.vue"
import SaveCategory from "@/views/admin/category-manage/components/save-category.vue";
import {ref} from "vue";
import type {TableColumnData} from "@arco-design/web-vue";
import {deleteCategory, treeCategory} from "@/api/category-manage";
import {Notification} from "@arco-design/web-vue";

const initSearchForm = (): CategorySearchForm => {
  return {
    categoryName: "",
    enabled: undefined
  }
}

const searchFormData = ref<CategorySearchForm>(initSearchForm())

/**
 * 查询按钮
 */
const handleSearch = () => {
  fetchTableData(searchFormData.value)
}

/**
 * 重置按钮
 */
const reset = () => {
  searchFormData.value = initSearchForm();
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

const categoryTableData = ref<TreeCategoryVO[]>([])

/**
 * 查询文章分类列表
 * @param form
 */
const fetchTableData = async (form: CategorySearchForm = {}) => {
  tableLoading.value = true
  try {
    const {data} = await treeCategory({...form})
    categoryTableData.value = data
  } finally {
    tableLoading.value = false
  }
}

fetchTableData()

const saveFormVisible = ref(false)
const saveCategoryFormData = ref<CategoryCreateForm>({})

/**
 * 新增分类
 */
const handleSaveCategory = () => {
  saveCategoryFormData.value = {
    enabled: true
  }
  saveFormVisible.value = true
}

/**
 * 新增子级分类
 * @param value
 */
const handleSaveChildCategory = (value: TreeCategoryVO) => {
  saveCategoryFormData.value = {
    parentCategoryId: value.categoryId,
    parentCategoryName: value.categoryName,
    enabled: true
  }
  saveFormVisible.value = true
}

const handleUpdateCategory = (value: TreeCategoryVO) => {
  saveCategoryFormData.value = {...value}
  saveFormVisible.value = true
}

const handleDeleteCategory = async (value: TreeCategoryVO) => {
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