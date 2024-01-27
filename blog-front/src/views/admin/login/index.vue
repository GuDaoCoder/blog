<template>
  <a-button type="primary" @click="login">登录</a-button>
</template>

<script setup lang="ts">
import {Notification} from '@arco-design/web-vue';
import {useRouter} from "vue-router";

const router = useRouter()

const login = () => {
  Notification.success("欢迎进入博客管理系统");
  router.push({name: "admin"})
}
</script>

<style scoped>

</style>