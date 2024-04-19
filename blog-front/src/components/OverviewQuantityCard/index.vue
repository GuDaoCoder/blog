<script lang="ts" setup>
const props = defineProps({
  name: {
    type: String,
    required: true
  },
  label: {
    type: String,
    required: true
  },
  quantity: {
    type: Number,
    default: 0
  }
})
</script>

<template>
  <a-card class="wrapper">
    <div class="content">
      <svg-icon :name="props.name?.toLowerCase()" size="60"/>
      <a-statistic :title="props.label" :value="props.quantity" animation/>
    </div>
  </a-card>
</template>

<style lang="scss" scoped>
.wrapper {
  height: 100px;
  padding: 0 20px 0 20px;

  .content {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
  }
}
</style>