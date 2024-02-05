<template>
  <div class="container">
    <div class="login-form-wrapper">
      <div class="login-form-title">博客管理系统</div>
      <div class="login-form-error-msg">{{ errorMessage }}</div>
      <a-form :model="loginFormModel" class="login-form" layout="vertical" @submit="handleLogin">
        <a-form-item field="username" tooltip="用户名" hide-label>
          <a-input
              v-model="loginFormModel.username"
              placeholder="用户名"
          >
            <template #prefix>
              <icon-user/>
            </template>
          </a-input>
        </a-form-item>
        <a-form-item field="password" tooltip="密码" hide-label>
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
import {Notification} from '@arco-design/web-vue';
import {useRouter} from "vue-router";
import {reactive, ref} from "vue";
import type {LoginRequestType} from "@/types/request-type";
import {login} from '@/api/login'

const router = useRouter()

const loading = ref<boolean>(false)
const errorMessage = ref<string>('')
const loginFormModel = reactive<LoginRequestType>({password: "", username: ""})

const handleLogin = () => {
  login(loginFormModel).then(response => {
    console.log(response);
    Notification.success("欢迎进入博客管理系统");
    router.push({name: "admin"})
  })

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