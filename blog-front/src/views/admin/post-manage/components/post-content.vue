<template>
  <a-space direction="vertical" fill>
    <md-edit ref="mdEditor" :content="content"/>
  </a-space>
</template>

<script setup lang="ts">
import MdEdit from "@/components/MdEdit/index.vue"
import {ref} from "vue";

defineProps({
  content: {
    type: String,
    default: ""
  }
});
const emit = defineEmits(['changeStep', 'update:content', 'cancel']);

const mdEditor = ref();

const handleNextStep = () => {
  emit("update:content", mdEditor.value.getValue())
  emit("changeStep", (step: number) => step + 1)
}

const handleCancel = () => {
  emit("cancel")
}
</script>

<style scoped>

</style>