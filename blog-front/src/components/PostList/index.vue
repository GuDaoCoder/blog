<script lang="ts" setup>
import {reactive, ref} from "vue";

const names = ref(['Socrates', 'Balzac', 'Plato']);
const avatarSrc = ref([
  'https://p1-arco.byteimg.com/tos-cn-i-uwbnlip3yd/a8c8cdb109cb051163646151a4a5083b.png~tplv-uwbnlip3yd-webp.webp',
  'https://p1-arco.byteimg.com/tos-cn-i-uwbnlip3yd/e278888093bef8910e829486fb45dd69.png~tplv-uwbnlip3yd-webp.webp',
  'https://p1-arco.byteimg.com/tos-cn-i-uwbnlip3yd/9eeb1800d9b78349b24682c3518ac4a3.png~tplv-uwbnlip3yd-webp.webp',
]);
const imageSrc = ref([
  'https://p1-arco.byteimg.com/tos-cn-i-uwbnlip3yd/cd7a1aaea8e1c5e3d26fe2591e561798.png~tplv-uwbnlip3yd-webp.webp',
  'https://p1-arco.byteimg.com/tos-cn-i-uwbnlip3yd/cd7a1aaea8e1c5e3d26fe2591e561798.png~tplv-uwbnlip3yd-webp.webp',
  'https://p1-arco.byteimg.com/tos-cn-i-uwbnlip3yd/cd7a1aaea8e1c5e3d26fe2591e561798.png~tplv-uwbnlip3yd-webp.webp',
]);
const dataSource = new Array(15).fill(null).map((_, index) => {
  return {
    index: index,
    avatar: avatarSrc.value[index % avatarSrc.value.length],
    title: names.value[index % names.value.length],
    description:
        'Beijing ByteDance Technology Co., Ltd. is an enterprise located in China. ByteDance has products such as TikTok, Toutiao, volcano video and Douyin (the Chinese version of TikTok).',
    imageSrc: imageSrc.value[index % imageSrc.value.length],
  };
});
const paginationProps = reactive({
  defaultPageSize: 10,
  total: dataSource.length
})
</script>

<template>
  <div class="list-wrapper">
    <a-list
        :bordered="false"
        :data="dataSource"
        :pagination-props="paginationProps"
    >
      <template #item="{ item }">
        <a-list-item action-layout="vertical" class="list-demo-item">
          <template #actions>
            <span><icon-heart/>83</span>
            <span><icon-star/>{{ item.index }}</span>
            <span><icon-message/>Reply</span>
          </template>
          <template #extra>
            <div className="image-area">
              <a-image :preview="false" :src="item.imageSrc" fit="cover" height="100px" width="200px"/>
            </div>
          </template>
          <a-list-item-meta
              :description="item.description"
              :title="item.title"
          >
          </a-list-item-meta>
        </a-list-item>
      </template>
    </a-list>
  </div>

</template>

<style lang="scss" scoped>
.list-wrapper {
  padding: 20px 0;
}
</style>