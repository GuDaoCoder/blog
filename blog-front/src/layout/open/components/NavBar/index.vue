<script lang="ts" setup>
import {useRouter} from "vue-router";

const router = useRouter();
const toHome = () => {
  router.push({path: "/home"})
}
</script>

<template>
  <div class="navbar">
    <div class="left-side">
      <img alt="logo"
           src="//p3-armor.byteimg.com/tos-cn-i-49unhts6dw/dfdba5317c0c20ce20e64fac803d52bc.svg~tplv-49unhts6dw-image.image"
           @click="toHome"
      />
    </div>

    <div class="right-side">
      <a-space>
        <a-input placeholder="请输入内容"/>
      </a-space>
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