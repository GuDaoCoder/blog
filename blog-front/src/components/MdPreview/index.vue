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
    height: '100vh',
    cache: {
      enable: false
    },
    outline: {
      enable: true,
      position: "right"
    }
  })
})
</script>

<style scoped>

</style>