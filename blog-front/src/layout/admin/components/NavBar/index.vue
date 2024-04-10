<template>
  <div class="navbar">
    <div class="left-side">
      <a-image :preview="false" :src="logo" class="pointer" height="40px" width="150px" @click="toHome"
      />
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
            <img :src="avatar" alt="avatar"/>
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
<script lang="ts" setup>
import {ref} from "vue";
import {useRouter} from "vue-router";
import {logout} from "@/api/admin/login";
import {clearToken} from "@/utils/auth";
import logo from "@/assets/logo.png"

const router = useRouter()

const avatar = ref('https://img.touxiangwu.com/zb_users/upload/2023/06/202306191687149562582619.jpg')

const handleLogout = () => {
  logout().then(() => {
    clearToken()
    router.push({name: "login"})
  })
}

const toHome = () => {
  router.push({name: "admin-home"})
}
</script>
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

