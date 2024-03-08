<template>
  <div class="navbar">
    <div class="left-side">
      <a-space>
        <img
            alt="logo"
            src="//p3-armor.byteimg.com/tos-cn-i-49unhts6dw/dfdba5317c0c20ce20e64fac803d52bc.svg~tplv-49unhts6dw-image.image"
        />
        <a-typography-title
            :style="{ margin: 0, fontSize: '18px' }"
            :heading="5"
        >
          博客管理系统
        </a-typography-title>
      </a-space>
    </div>
    <div class="right-side">
      <a-space>
        <a-button type="text">
          <template #icon>
            <svg-icon name="search"/>
          </template>
        </a-button>
        <a-button type="text">
          <template #icon>
            <svg-icon name="setting"/>
          </template>
        </a-button>

        <a-dropdown trigger="click">
          <a-avatar
              :size="32"
              :style="{cursor: 'pointer' }"
          >
            <img alt="avatar" :src="avatar"/>
          </a-avatar>
          <template #content>
            <a-doption>
              <a-space>
                <svg-icon name="user"/>
                <span>
                  用户中心
                </span>
              </a-space>
            </a-doption>
            <a-doption>
              <a-space @click="handleLogout">
                <svg-icon name="logout"/>
                <span>
                  登出
                </span>
              </a-space>
            </a-doption>
          </template>
        </a-dropdown>
      </a-space>
    </div>
  </div>
</template>
<script setup lang="ts">
import {ref} from "vue";
import {useRouter} from "vue-router";
import {logout} from "@/api/admin/login";
import {clearToken} from "@/utils/auth";

const router = useRouter()

const avatar = ref('https://img.touxiangwu.com/zb_users/upload/2023/06/202306191687149562582619.jpg')

const handleLogout = () => {
  logout().then(() => {
    clearToken()
    router.push({name: "login"})
  })
}
</script>
<style scoped lang="scss">
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

