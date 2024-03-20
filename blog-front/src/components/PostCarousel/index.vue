<script lang="ts" setup>
import {ref} from "vue";

const images = ref([
  'https://p1-arco.byteimg.com/tos-cn-i-uwbnlip3yd/cd7a1aaea8e1c5e3d26fe2591e561798.png~tplv-uwbnlip3yd-webp.webp',
  'https://p1-arco.byteimg.com/tos-cn-i-uwbnlip3yd/6480dbc69be1b5de95010289787d64f1.png~tplv-uwbnlip3yd-webp.webp',
  'https://p1-arco.byteimg.com/tos-cn-i-uwbnlip3yd/0265a04fddbd77a19602a15d9d55d797.png~tplv-uwbnlip3yd-webp.webp',
]);
</script>

<template>
  <a-carousel
      :autoPlay="true"
      :style="{
      width: '100%',
      height: '300px',
    }"
      animation-name="card"
      indicator-position="outer"
      show-arrow="never"
  >
    <a-carousel-item v-for="image in images" style="width: 60%">
      <a-image
          :preview="false"
          :src="image"
          fit="cover"
          width="100%"/>
    </a-carousel-item>
  </a-carousel>
</template>

<style scoped>

</style>