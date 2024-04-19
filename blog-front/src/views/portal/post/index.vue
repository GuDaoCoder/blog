<script lang="ts" setup>
import MdPreview from "@/components/MdPreview/index.vue"
import {onMounted, ref} from "vue";
import {formatStandStr} from "@/utils/date"
import postApi from "@/api/post/index";
import {useRoute} from "vue-router";
import type {PostDetailResponse} from "@/api/post/types";

const route = useRoute()

const post = ref<PostDetailResponse>({})

onMounted(() => {
  let postId = route.query.postId;
  if (postId) {
    fetchPostDetail(postId);
  }
})
const fetchPostDetail = async (postId: number) => {
  const {data} = await postApi.portalGetPostDetail(postId)
  post.value = data
}

</script>

<template>
  <div class="post-wrapper">
    <h1 class="post-title">
      {{ post.title }}
    </h1>
    <div class="post-base-info">
      <a-space>
        <span>{{ formatStandStr(post.updateTime, "YYYY-MM-DD") }}</span>
        <!--        <span>|</span>-->
        <!--        <span>0 评论</span>-->
        <!--        <span>|</span>-->
        <!--        <span>0 点赞</span>-->
        <!--        <span>|</span>-->
        <!--        <span>0 阅读</span>-->
      </a-space>
    </div>
    <md-preview :text="post.content"/>
    <a-divider/>
  </div>
</template>

<style lang="scss" scoped>
.post-wrapper {
  background-color: #FFFFFF;
}

.post-title {
  font-size: 24px;
  color: #333;
  text-align: center;
  padding-top: 40px;
  word-break: break-word;
  text-shadow: 0 1px 2px rgba(0, 0, 0, 0.25);
}

.post-base-info {
  font-size: 16px;
  padding: 20px 40px;
  color: #909399;
}

.post-actions {
  padding: 20px 0;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>