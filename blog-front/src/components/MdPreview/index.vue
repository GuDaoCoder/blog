<template>
  <div
      v-for="anchor in titles"
      :style="{ padding: `10px 0 10px ${anchor.indent * 20}px` }"
  >
    <a style="cursor: pointer">{{ anchor.title }}</a>
  </div>
  <v-md-preview ref="preview" :text="props.text" height="400px"
                @copy-code-success="handleCopyCodeSuccess"></v-md-preview>
</template>

<script lang="ts" setup>
import {onMounted, ref} from "vue";
import {Message} from '@arco-design/web-vue';

const props = defineProps({
  text: {
    type: String,
    default: ""
  }
})
const titles = ref<any[]>([])
const preview = ref()

onMounted(() => {

  const anchors = preview.value.$el.querySelectorAll('h1,h2,h3,h4,h5,h6');

  const _titles = Array.from(anchors).filter((title) => !!title.innerText.trim());

  if (!_titles.length) {
    titles.value = [];
    return;
  }

  const hTags = Array.from(new Set(_titles.map((title) => title.tagName))).sort();

  titles.value = _titles.map((el) => ({
    title: el.innerText,
    lineIndex: el.getAttribute('data-v-md-line'),
    indent: hTags.indexOf(el.tagName),
  }));
})

const handleCopyCodeSuccess = () => {
  Message.success("复制成功")
}
</script>

<style scoped>

</style>