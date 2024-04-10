<script lang="ts" setup>
import {useRouter} from "vue-router";
import logo from "@/assets/logo.png"

const router = useRouter();
const toHome = () => {
  router.push({path: "/home"})
}
</script>

<template>
  <div class="navbar">
    <div class="left-side">
      <a-image :preview="false" :src="logo" class="pointer" height="40px" width="150px" @click="toHome"
      />
    </div>

    <div class="right-side">
      <!--      <a-space>-->
      <!--        <a-input placeholder="请输入内容"/>-->
      <!--      </a-space>-->
    </div>
  </div>
</template>

<style lang="scss" scoped>
.navbar {
  display: flex;
  justify-content: space-between;
  height: 100%;
  background-color: var(--color-bg-2);
  border-bottom: 1px solid var(--color-border);
}

.left-side {
  display: flex;
  align-items: center;
  padding-left: 20px;
}

.right-side {
  display: flex;
  align-items: center;
  padding-right: 20px;
}
</style>