<script lang="ts" setup>
import type {PropType} from "vue";
import {useRouter} from "vue-router";
import {formatStandStr} from "@/utils/date";

const props = defineProps({
  data: {
    type: Object as PropType<PostListProp>,
    default: {
      items: []
    }
  }
})

const router = useRouter()
const toPost = (post: BlogPostResponse) => {
  router.push({path: "/post", query: {postId: post.postId}})
}
</script>

<template>
  <div class="list-wrapper">
    <a-list
        :bordered="false"
        :data="props.data.items"
    >
      <template #item="{ item }">
        <a-list-item action-layout="vertical">
          <template #actions>
            <span class="action">{{ item.categoryName }}</span>
            <span>{{ formatStandStr(item.updateTime, "YYYY-MM-DD") }}</span>
          </template>
          <template #extra>
            <a-image :preview="false" :src="item.coverPictureUrl" fit="cover" height="100px" style=" cursor: pointer "
                     width="200px"
                     @click="toPost(item )"/>
          </template>
          <a-list-item-meta
              :description="item.summary"
              :title="item.title"
          >
            <template #title>
              <span class="title text-animation pointer" @click="toPost(item)">{{ item.title }}</span>
            </template>
            <template #description>
              <span class="summary pointer text-animation" @click="toPost(item)">{{ item.summary }}</span>
              <div style="padding: 10px 0">
                <a-space>
                  <a-tag v-for="tag in item.tags" :color="tag.color" :index="tag.tagId" class="pointer">{{
                      tag.tagName
                    }}
                  </a-tag>
                </a-space>
              </div>
            </template>
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

.text-animation {
  background: linear-gradient(to right, #2D5CF6, #2D5CF6) no-repeat right bottom;
  background-size: 0 2px;
  transition: background-size 0.5s;

  &:hover {
    background-position-x: left;
    background-size: 100% 2px;
  }
}

.title {
  font-size: 18px;
}

.summary {
  color: #85888E;
}

::v-deep(.arco-list-item-extra) {
  display: flex;
  flex-direction: column;
}

::v-deep(.arco-list-item-action) {
  color: #85888E;
}

.action {
  &:hover {
    color: #2D5CF6;
  }
}

</style>