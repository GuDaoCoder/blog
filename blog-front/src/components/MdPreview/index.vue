<template>
  <v-md-preview id="md-preview" ref="preview" :text="props.text" height="400px"
                @copy-code-success="handleCopyCodeSuccess"></v-md-preview>
</template>

<script lang="ts" setup>
import {onUpdated, ref} from "vue";
import {Message} from '@arco-design/web-vue';
import type {Anchor} from "@/store/modules/anchor/types";
import useAnchorStore from "@/store/modules/anchor";

const props = defineProps({
  text: {
    type: String,
    default: ""
  }
})

const preview = ref()

const anchorStore = useAnchorStore();

onUpdated(() => {
  const anchors: Anchor[] = handleGetAnchors();
  anchorStore.updateAnchorState({
    el: preview.value,
    anchors: anchors
  })
})

const handleGetAnchors = (): Anchor[] => {
  const elements = preview.value.$el.querySelectorAll('h1,h2,h3,h4,h5,h6');
  const filterElements = Array.from(elements).filter(element => !!element.innerText.trim());
  if (filterElements.length > 0) {
    const hTags = Array.from(new Set(filterElements.map(element => element.tagName))).sort();
    return filterElements.map(element => ({
      title: element.innerText,
      lineIndex: element.getAttribute('data-v-md-line'),
      indent: hTags.indexOf(element.tagName),
    }));
  }
  return [];
}

const handleCopyCodeSuccess = () => {
  Message.success("复制成功")
}
</script>

<style scoped>

</style>