<script lang="ts" setup>
import {onMounted, ref} from "vue";
import type {GitRepositoryForm} from "@/api/git-repository/types";
import gitRepositoryApi from "@/api/git-repository";
import type {ValidatedError} from "@arco-design/web-vue";
import {Notification} from "@arco-design/web-vue";

const formLoading = ref(false)
const loading = ref(false)
const form = ref<GitRepositoryForm>({})
const rules = ref({
  url: [
    {
      type: 'url',
      required: true,
    }
  ],
  localPath: [
    {
      required: true,
      message: '请输入本地下载路径',
    }
  ],
})

onMounted(() => {
  fetchGitRepository()
})

const fetchGitRepository = async () => {
  formLoading.value = true
  try {
    const {data} = await gitRepositoryApi.list();
    if (data && data.length > 0) {
      form.value = data[0]
    }
  } finally {
    formLoading.value = false
  }
}

const handleSubmit = async ({values, errors}: {
  errors: Record<string, ValidatedError> | undefined;
  values: Record<string, any>;
}) => {
  if (loading.value) {
    return
  }
  if (!errors) {
    loading.value = true
    try {
      await gitRepositoryApi.save({...values})
      await fetchGitRepository()
      Notification.success("保存成功");
    } finally {
      loading.value = false
    }
  }
};
</script>

<template>
  <a-spin :loading="formLoading" dot>
    <a-form :model="form" :rules="rules" :style="{ width: '600px' }" @submit="handleSubmit">
      <a-form-item field="url" label="Url">
        <a-input v-model="form.url"/>
      </a-form-item>
      <a-form-item field="localPath" label="本地下载路径">
        <a-input v-model="form.localPath"/>
      </a-form-item>
      <a-form-item field="branch" label="分支">
        <a-input v-model="form.branch"/>
        <template #extra>
          <div>没有指定分支则从使用默认分支</div>
        </template>
      </a-form-item>
      <a-form-item field="username" label="用户名">
        <a-input v-model="form.username"/>
        <template #extra>
          <div>如果担心泄露账号信息，可以将仓库设为public</div>
        </template>
      </a-form-item>
      <a-form-item field="password" label="密码">
        <a-input-password v-model="form.password" allow-clear/>
      </a-form-item>
      <a-form-item>
        <a-button :loading="loading" html-type="submit" type="primary">保存</a-button>
      </a-form-item>
    </a-form>
  </a-spin>
</template>

<style lang="scss" scoped>

</style>