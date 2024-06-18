<script lang="ts" setup>
import {IconEdit, IconPlus} from '@arco-design/web-vue/es/icon';
import {computed, ref} from "vue";
import type {FileItem} from "@arco-design/web-vue";

const props = defineProps({
  url: {
    type: String,
    required: true
  }
})

const getUrl = computed(() => {
  return props.url;
})

const file = ref<FileItem>({uid: "1", url: getUrl.value});

const onChange = (_: FileItem[], currentFile: FileItem) => {
  file.value = {
    ...currentFile,
  };
};
const onProgress = (currentFile: FileItem) => {
  file.value = currentFile;
};
</script>

<template>
  <a-upload
      :fileList="file ? [file] : []"
      :show-file-list="false"
      action="/"
      @change="onChange"
      @progress="onProgress"
  >
    <template #upload-button>
      <div
          :class="`arco-upload-list-item${
            file && file.status === 'error' ? ' arco-upload-list-item-error' : ''
          }`"
      >
        <div
            v-if="file && file.url"
            class="arco-upload-list-picture custom-upload-avatar"
        >
          <img :src="file.url" alt=""/>
          <div class="arco-upload-list-picture-mask">
            <IconEdit/>
          </div>
          <a-progress
              v-if="file.status === 'uploading' && file.percent < 100"
              :percent="file.percent"
              :style="{
                position: 'absolute',
                left: '50%',
                top: '50%',
                transform: 'translateX(-50%) translateY(-50%)',
              }"
              size="mini"
              type="circle"
          />
        </div>
        <div v-else class="arco-upload-picture-card">
          <div class="arco-upload-picture-card-text">
            <IconPlus/>
            <div style="margin-top: 10px; font-weight: 600">Upload</div>
          </div>
        </div>
      </div>
    </template>
  </a-upload>
</template>

<style lang="scss" scoped>

</style>