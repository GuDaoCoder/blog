<script lang="ts" setup>
import ContentCard from "@/components/ContentCard/index.vue";
</script>

<template>
  <Container>
    <content-card style="height: 100%">
      <a-tabs animation destroy-on-hide justify lazy-load type="card">
        <a-tab-pane key="3" title="OSS">
          OSS
        </a-tab-pane>
      </a-tabs>
    </content-card>
  </Container>
</template>

<style lang="scss" scoped>

</style>