<script lang="ts" setup>
import type {PropType} from "vue";
import {reactive} from "vue";
import {useRouter} from "vue-router";
import {formatStandStr} from '@/utils/date'

const props = defineProps({
  data: {
    type: Object as PropType<PostListProp>,
    default: {
      items: []
    }
  }
})

const paginationProps = reactive({
  defaultPageSize: 10,
  total: props.data?.items.length
})

const router = useRouter()
const toPost = () => {
  router.push({path: "/post"})
}
</script>

<template>
  <div class="list-wrapper">
    <a-list
        :bordered="false"
        :data="props.data.items"
        :pagination-props="paginationProps"
    >
      <template #item="{ item }">
        <a-list-item action-layout="vertical">
          <template #actions>
            <span><icon-heart/>83</span>
            <span><icon-star/>10</span>
            <span><icon-message/>Reply</span>
          </template>
          <template #extra>
            <a-image :preview="false" :src="item.coverPictureUrl" fit="cover" height="100px" width="200px"/>
            <div style="flex: 1;position: relative;">
              <span style="position: absolute;right:0; bottom: 0">{{
                  formatStandStr(item.publishTime, "YYYY-MM-DD")
                }}</span>
            </div>
          </template>
          <a-list-item-meta
              :description="item.summary"
              :title="item.title"
          >
            <template #title>
              <h2>{{ item.title }}</h2>
            </template>
            <template #description>
              <p class="hover-text" @click="toPost">{{ item.summary }}</p>
              <div style="padding: 10px 0">
                <a-space>
                  <a-tag v-for="tag in item.tags" :color="tag.color" :index="tag.tagId">{{ tag.tagName }}</a-tag>
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

.hover-text {
  color: black;
  text-decoration: none;
  transition: color 0.3s;
}

.hover-text:hover {
  cursor: pointer;
  color: blue;
  text-decoration: underline;
}

::v-deep(.arco-list-item-extra) {
  display: flex;
  flex-direction: column;
}
</style>