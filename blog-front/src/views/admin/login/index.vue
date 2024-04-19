<template>
  <div class="container">
    <div class="login-form-wrapper">
      <div class="login-form-title">博客管理系统</div>
      <a-form :model="loginForm" :rules="formRules" class="login-form" layout="vertical" @submit="handleLogin">
        <a-form-item field="username" hide-label tooltip="用户名">
          <a-input
              v-model="loginForm.username"
              placeholder="用户名"
          >
            <template #prefix>
              <icon-user/>
            </template>
          </a-input>
        </a-form-item>
        <a-form-item field="password" hide-label tooltip="密码">
          <a-input-password
              v-model="loginForm.password"
              allow-clear
              placeholder="密码"
          >
            <template #prefix>
              <icon-lock/>
            </template>
          </a-input-password>
        </a-form-item>
        <a-space :size="16" direction="vertical">
          <div class="login-form-password-actions">
            <a-checkbox>记住密码</a-checkbox>
            <a-link>忘记密码</a-link>
          </div>

          <a-button :loading="loading" html-type="submit" long type="primary">
            登录
          </a-button>
        </a-space>

      </a-form>
    </div>
  </div>
</template>

<script lang="ts" setup>
import {Notification, type ValidatedError} from '@arco-design/web-vue';
import {useRouter} from "vue-router";
import {reactive, ref} from "vue";
import {setToken} from "@/utils/auth";
import loginApi from "@/api/login/index"
import type {LoginForm, LoginRequest} from "@/api/login/types";

const router = useRouter()

const loading = ref<boolean>(false)
const loginForm = reactive<LoginForm>({username: "admin", password: "136415Zzp++"})

const formRules = reactive({
  username: [{
    required: true,
    message: '用户名不能为空'
  }
  ],
  password: [
    {
      required: true,
      message: '用户名不能为空'
    }
  ]
})

const handleLogin = async ({values, errors}: {
  errors: Record<string, ValidatedError> | undefined;
  values: Record<string, any>;
}) => {
  if (loading.value) {
    return
  }
  if (!errors) {
    loading.value = true
    try {
      const {data: {token}} = await loginApi.login(values as LoginRequest)
      setToken(token)
      Notification.success("欢迎进入博客管理系统");
      await router.push({name: "admin"})
    } finally {
      loading.value = false
    }
  }
}
</script>

<style lang="scss" scoped>
.container {
  background-color: #FFFFFF;
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
}

.login-container {
  padding: 20px;
}

.login-form {
  &-wrapper {
    width: 320px;
  }

  &-title {
    color: var(--color-text-1);
    font-weight: 500;
    font-size: 24px;
    line-height: 32px;
    margin-bottom: 20px;
  }

  &-error-msg {
    height: 32px;
    color: rgb(var(--red-6));
    line-height: 32px;
  }

  &-password-actions {
    display: flex;
    justify-content: space-between;
  }
}

</style>