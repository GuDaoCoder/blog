<template>
  <div id="vditor"></div>
</template>

<script setup lang="ts">
import Vditor from 'vditor'
import 'vditor/dist/index.css';
import {ref, onMounted} from 'vue';
import {getToken} from "@/utils/auth";

var props = defineProps({
  content: {
    type: String,
    default: ""
  }
});

const vditor = ref()
onMounted(() => {
  vditor.value = new Vditor('vditor', {
    value: props.content,
    width: '100%',
    height: 'calc(100vh - 200px)',
    cache: {
      enable: false
    },
    outline: {
      enable: true,
      position: "right"
    },
    upload: {
      url: `${import.meta.env.VITE_APP_BASE_API}/admin/oss/uploadImageBed`,
      linkToImgUrl: `${import.meta.env.VITE_APP_BASE_API}/admin/oss/uploadImageBed`,
      accept: 'image/jpeg,image/png,image/gif,image/jpg,image/bmp',
      multiple: false, // 是否允许批量上传
      fieldName: 'file',// 上传字段名称
      withCredentials: true,
      headers: {"Authorization": getToken()},
      format: (files: File[], responseText: string) => {
        const res = JSON.parse(responseText);
        const name = files[0].name;
        const url = res.data.url;
        return JSON.stringify({
          code: 0,
          data: {errFiles: '', succMap: {[name]: url}},
        });
      }
    }
  })
})

const getValue = () => {
  return vditor.value.getValue();
}

defineExpose({getValue})
</script>

<style scoped>

</style>