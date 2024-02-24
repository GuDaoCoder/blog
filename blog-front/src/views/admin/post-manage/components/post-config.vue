<template>
  <a-form :model="formData" @submit="handleSavePost" class="post-form-wrapper">
    <a-form-item label="文章标题">
      <a-input v-model="formData.title"/>
    </a-form-item>

    <a-form-item label="文章分类">
      <a-tree-select
          v-model="formData.categoryId"
          placeholder="全部"
      ></a-tree-select>
    </a-form-item>

    <a-form-item label="摘要">
      <a-textarea v-model="formData.summary" show-word-limit allow-clear auto-size :max-length="100"/>
    </a-form-item>

    <a-form-item label="标签">
      <a-input-tag :max-tag-count="5" allow-clear/>
    </a-form-item>

    <a-form-item label="封面">
      <a-upload action="/" image-preview :limit="1" list-type="picture" show-preview-button/>
    </a-form-item>

    <a-form-item label="是否置顶">
      <a-switch v-model="formData.top"/>
    </a-form-item>

    <a-form-item label="是否开启评论">
      <a-switch v-model="formData.enableComment"/>
    </a-form-item>

    <a-form-item label="是否加密">
      <a-switch v-model="formData.encrypt"/>
    </a-form-item>
  </a-form>
</template>

<script setup lang="ts">
import OperationsGroup from "@/components/OperationsGroup/index.vue";
import {type PropType, ref} from "vue";
import type {FieldString} from "@arco-design/web-vue/es/_utils/types";

const props = defineProps({
  formData: {
    type: Object as PropType<SavePostForm>,
    default: {}
  }
})

const emit = defineEmits(['changeStep']);

const fieldData = ref<FieldString<string>>("test")

const handleSavePost = () => {
  console.log(props.formData);
}

const handleBackStep = () => {
  emit("changeStep", (step: number) => step - 1)
}
</script>


<style scoped lang="scss">
.post-form-wrapper {
  width: 380px;
  margin: 0 auto;

}
</style>