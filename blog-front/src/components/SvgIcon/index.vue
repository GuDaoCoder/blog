<template>
  <svg :width="size" :height="size">
    <use :xlink:href="'#icon-'+props.name" :fill="color"></use>
  </svg>
</template>

<script setup lang="ts">
const props = withDefaults(defineProps<{
      // 图标名称
      name: string,
      // 颜色
      color?: string,
      // 尺寸
      size?: number
    }>(),
    {
      size: () => 32
    });
</script>