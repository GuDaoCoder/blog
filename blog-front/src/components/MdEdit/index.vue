<template>
  <div id="vditor"></div>
</template>

<script setup lang="ts">
import Vditor from 'vditor'
import 'vditor/dist/index.css';
import {ref, onMounted} from 'vue';

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