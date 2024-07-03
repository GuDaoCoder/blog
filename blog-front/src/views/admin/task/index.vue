<script lang="ts" setup>
import ContentCard from "@/components/ContentCard/index.vue";
import {onMounted, ref} from "vue";
import type {TaskResponse, TaskSearchForm} from "@/api/task/types";
import SearchButtonGroup from "@/components/SearchButtonGroup/index.vue";
import TaskLog from "@/views/admin/task/components/TaskLog/index.vue"
import {type TableColumnData} from "@arco-design/web-vue";
import SearchResult from "@/components/SearchResult/index.vue";
import Pagination from "@/components/Pagination/index.vue";
import taskApi from "@/api/task/index"
import {TaskStatus} from "@/enums";
import {useDict} from "@/utils/dict";

const initSearchForm = () => {
  return {
    taskName: "",
    status: ""
  }
}

const searchForm = ref<TaskSearchForm>(initSearchForm())

const handleSearch = () => {
  fetchTableData()
}

const reset = () => {
  initSearchForm();
  fetchTableData();
}

const tableColumns = ref<TableColumnData[]>([
  {
    title: "任务名称",
    dataIndex: "taskName"
  },
  {
    title: "任务状态",
    dataIndex: "status",
    slotName: "status"
  },
  {
    title: "任务开始时间",
    dataIndex: "beginDateTime",
  },
  {
    title: "任务结束时间",
    dataIndex: "endDateTime",
  },
  {
    title: "日志信息",
    dataIndex: "log",
    slotName: "log"
  }
])

const tableLoading = ref<boolean>(false)

const taskTableData = ref<TaskResponse[]>([])


const pagination = ref<PaginationType>(
    {
      pageNumber: 1,
      pageSize: 10,
      total: 0
    })

const fetchTableData = async () => {
  if (tableLoading.value) {
    return
  }
  tableLoading.value = true
  try {
    let params = {
      pageNumber: pagination.value.pageNumber,
      pageSize: pagination.value.pageSize,
      ...searchForm.value
    }
    const {data} = await taskApi.searchTasks(params)
    taskTableData.value = data.items || []
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

const handleChangePage = (pageNumber: number) => {
  pagination.value.pageNumber = pageNumber
  fetchTableData()
}

const handleChangePageSize = (pageSize: number) => {
  pagination.value.pageSize = pageSize
  fetchTableData()
}

const taskLogVisible = ref<boolean>(false)
const taskLog = ref()

const handleOpenTaskLog = async (record: TaskResponse) => {
  taskLogVisible.value = true
  taskLog.value = record.log
}

const handleCancelTaskLogDialog = () => {
  taskLogVisible.value = false
}
</script>

<template>
  <Container>
    <content-card>
      <a-form :model="searchForm" @submit="handleSearch">
        <a-row :gutter="16">
          <a-col :span="8">
            <a-form-item label="任务名称">
              <a-input v-model="searchForm.taskName"/>
            </a-form-item>
          </a-col>

          <a-col :span="8">
            <a-form-item label="状态">
              <a-select v-model="searchForm.status" placeholder="全部">
                <a-option v-for="[key,value] in Object.entries(TaskStatus)" :key="key" :value="key">{{ value }}
                </a-option>
              </a-select>
            </a-form-item>
          </a-col>
        </a-row>

        <search-button-group>
          <a-button html-type="submit" type="primary">查询</a-button>
          <a-button type="outline" @click="reset">重置</a-button>
        </search-button-group>
      </a-form>

      <search-result>
        <template #table>
          <a-table :columns="tableColumns" :data="taskTableData" :loading="tableLoading" :pagination="false"
                   column-resizable
                   row-key="taskId" stripe>
            <template #status="{record}">
              {{ useDict(TaskStatus, record.status) }}
            </template>
            <template #log="{record}">
              <a-link @click="handleOpenTaskLog(record)">查看日志信息</a-link>
            </template>
          </a-table>
        </template>

        <template #pagination>
          <Pagination :pagination="pagination" @change="handleChangePage" @page-size-change="handleChangePageSize"/>
        </template>
      </search-result>
    </content-card>
  </Container>

  <task-log :log="taskLog" :visible="taskLogVisible" @cancel="handleCancelTaskLogDialog"/>

</template>

<style lang="scss" scoped>

</style>