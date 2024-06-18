<script lang="ts" setup>
import ContentCard from "@/components/ContentCard/index.vue";
import GitRepository from "@/views/admin/blog-setting/components/GitRepository/index.vue"
import AdminUser from "@/views/admin/admin-user/index.vue"
</script>

<template>
  <Container>
    <content-card style="height: 100%">
      <a-tabs animation destroy-on-hide justify lazy-load type="card">
        <a-tab-pane key="1" title="个人信息">
          <admin-user/>
        </a-tab-pane>
        <a-tab-pane key="2" title="Git仓库">
          <git-repository/>
        </a-tab-pane>
        <a-tab-pane key="3" title="OSS">
          OSS
        </a-tab-pane>
      </a-tabs>
    </content-card>
  </Container>
</template>

<style lang="scss" scoped>

</style>