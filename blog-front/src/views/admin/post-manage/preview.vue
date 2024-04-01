<script lang="ts" setup>
import MdPreview from "@/components/MdPreview/index.vue"
import {useRouter} from "vue-router";
import {onBeforeMount, ref} from "vue";
import {getPostContent} from "@/api/admin/post";

const router = useRouter();

const content = ref<string>()

onBeforeMount(() => {
  const postId = router.currentRoute.value.query.postId;
  content.value = getContent(postId)
})

const getContent = async (postId:number):string => {
  const response =  await getPostContent(postId)
  return response.data
}
</script>

<template>
  <md-preview v-if="content" :content="content"/>
</template>

<style scoped>

</style>