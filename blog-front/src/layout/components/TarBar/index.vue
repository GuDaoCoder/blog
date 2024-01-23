<template>
  <div class="tab-bar-container">
    <a-affix ref="affixRef" :offset-top="offsetTop">
      <div class="tab-bar-box">
        <div class="tab-bar-scroll">
          <div class="tags-wrap">
            <!--            <tab-item-->
            <!--                v-for="(tag, index) in tagList"-->
            <!--                :key="tag.fullPath"-->
            <!--                :index="index"-->
            <!--                :item-data="tag"-->
            <!--            />-->
          </div>
        </div>
        <div class="tag-bar-operation"></div>
      </div>
    </a-affix>
  </div>
</template>

<script setup lang="ts">
import {ref} from 'vue'

const offsetTop = ref<number>(60);
</script>

<style lang="scss" scoped>
.tab-bar-container {
  position: relative;
  background-color: #fff;

  .tab-bar-box {
    display: flex;
    padding: 0 0 0 20px;
    background-color: #fff;
    border-bottom: 1px solid rgb(229,230,235);

    .tab-bar-scroll {
      height: 32px;
      flex: 1;
      overflow: hidden;

      .tags-wrap {
        padding: 4px 0;
        height: 48px;
        white-space: nowrap;
        overflow-x: auto;

        :deep(.arco-tag) {
          display: inline-flex;
          align-items: center;
          margin-right: 6px;
          cursor: pointer;

          &:first-child {
            .arco-tag-close-btn {
              display: none;
            }
          }
        }
      }
    }
  }

  .tag-bar-operation {
    width: 100px;
    height: 32px;
  }
}
</style>