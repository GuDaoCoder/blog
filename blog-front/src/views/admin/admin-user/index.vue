<script lang="ts" setup>

import {computed, onMounted, ref} from "vue";
import type {AdminUserForm} from "@/api/admin-user/types";
import adminUserApi from "@/api/admin-user";
import {getRadioOptions} from "@/utils/dict";
import {Gender} from "@/enums";
import type {FieldRule, FileItem, ValidatedError} from "@arco-design/web-vue";
import {Notification} from "@arco-design/web-vue";
import AvatarUpload from "@/components/AvatarUpload/index.vue"
import ContentCard from "@/components/ContentCard/index.vue";

const formLoading = ref(false)
const submitLoading = ref(false)
const form = ref<AdminUserForm>({})
const rules = ref<Record<string, FieldRule[]>>({
  username: [
    {
      required: true,
      message: '请输入用户名',
    }
  ],
  password: [
    {
      required: true,
      message: '请输入密码',
    }
  ],
  nickName: [
    {
      required: true,
      message: '请输入昵称',
    }
  ],
  sex: [
    {
      required: true,
      message: '请输入性别',
    }
  ],
  email: [
    {
      type: 'email',
      required: true,
    }
  ],

})

onMounted(() => {
  fetchAdminUser()
})

const fetchAdminUser = async () => {
  if (formLoading.value) {
    return
  }
  formLoading.value = true
  try {
    const {data} = await adminUserApi.list();
    if (data && data.length > 0) {
      form.value = data[0]
    }
  } finally {
    formLoading.value = false
  }
}

const handleSubmit = async ({values, errors}: {
  errors: Record<string, ValidatedError> | undefined;
  values: AdminUserForm;
}) => {
  if (submitLoading.value) {
    return
  }
  if (!errors) {
    submitLoading.value = true
    try {
      await adminUserApi.saveOrUpdate({...values})
      await fetchAdminUser()
      Notification.success("保存成功");
    } finally {
      submitLoading.value = false
    }
  }
};

const avatarFile = computed((): FileItem => {
  return {uid: '1', url: form.value.avatar};
})

const handleUploadSuccess = (fileItem: FileItem) => {
  Notification.success("头像上传成功");
  form.value.avatar = fileItem.response.data.url
}
</script>

<template>
  <Container>
    <content-card>
      <a-spin :loading="formLoading" dot>
        <a-form :model="form" :rules="rules" :style="{ width: '600px' }" @submit="handleSubmit">
          <a-form-item field="username" label="用户名">
            <a-input v-model="form.username"/>
          </a-form-item>
          <a-form-item field="password" label="密码">
            <a-input-password v-model="form.password" allow-clear/>
          </a-form-item>
          <a-form-item field="nickName" label="昵称">
            <a-input v-model="form.nickName"/>
          </a-form-item>
          <a-form-item label="头像">
            <avatar-upload/>
          </a-form-item>
          <a-form-item field="sex" label="性别">
            <a-radio-group v-model="form.sex" :options="getRadioOptions(Gender)"/>
          </a-form-item>
          <a-form-item field="email" label="邮箱">
            <a-input v-model="form.email"/>
          </a-form-item>
          <a-form-item disabled field="lastLoginIp" label="最后登录Ip">
            <a-input v-model="form.lastLoginIp"/>
          </a-form-item>
          <a-form-item disabled field="lastLoginTime" label="最后登录时间">
            <a-input v-model="form.lastLoginTime"/>
          </a-form-item>
          <a-form-item>
            <a-button :loading="submitLoading" html-type="submit" type="primary">保存</a-button>
          </a-form-item>
        </a-form>
      </a-spin>
    </content-card>
  </Container>

</template>

<style lang="scss" scoped>

</style>