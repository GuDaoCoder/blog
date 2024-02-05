<template>
  <div class="container">
    <div class="login-form-wrapper">
      <div class="login-form-title">博客管理系统</div>
      <a-form :model="loginFormModel" class="login-form" layout="vertical" @submit="handleLogin">
        <a-form-item field="username" tooltip="用户名" hide-label :rules="usernameRules">
          <a-input
              v-model="loginFormModel.username"
              placeholder="用户名"
          >
            <template #prefix>
              <icon-user/>
            </template>
          </a-input>
        </a-form-item>
        <a-form-item field="password" tooltip="密码" hide-label :rules="passwordRules">
          <a-input-password
              v-model="loginFormModel.password"
              placeholder="密码"
              allow-clear
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

          <a-button type="primary" html-type="submit" long :loading="loading">
            登录
          </a-button>
        </a-space>

      </a-form>
    </div>
  </div>
</template>

<script setup lang="ts">
import {type FieldRule, type ValidatedError, Notification} from '@arco-design/web-vue';
import {useRouter} from "vue-router";
import {reactive, ref} from "vue";
import type {LoginRequestType} from "@/types/request-type";
import {login} from '@/api/login'
import {setToken} from "@/utils/auth";

const router = useRouter()

const loading = ref<boolean>(false)
const loginFormModel = reactive<LoginRequestType>({username: "admin", password: "136415Zzp++"})
const usernameRules = reactive<FieldRule[]>([
  {
    required: true,
    message: '用户名不能为空'
  }
])
const passwordRules = reactive<FieldRule[]>([
  {
    required: true,
    message: '密码不能为空'
  }
])
const handleLogin = ({values, errors}: {
  errors: Record<string, ValidatedError> | undefined;
  values: Record<string, any>;
}) => {
  if (loading.value) {
    return
  }
  if (!errors) {
    loading.value = true
    try {
      login(loginFormModel).then(response => {
        setToken(response.data.token)
        Notification.success("欢迎进入博客管理系统");
        router.push({name: "admin"})
      })
    } finally {
      loading.value = false
    }
  }
}
</script>

<style scoped lang="scss">
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