<template>
  <v-md-preview :text="text" height="400px" @copy-code-success="handleCopyCodeSuccess"></v-md-preview>
</template>

<script lang="ts" setup>
import {onMounted, ref} from "vue";
import {Message} from '@arco-design/web-vue';
import {getPostContent} from "@/api/blog/post";

const props = defineProps({
  postId: {
    type: Number,
    required: true
  }
})

const text = ref()

onMounted(() => {
  fetchPostContent(props.postId)
})

const fetchPostContent = async (postId: number) => {
  const {data} = await getPostContent(postId)
  text.value = data
}

const handleCopyCodeSuccess = () => {
  Message.success("复制成功")
}
</script>

<style scoped>

</style>