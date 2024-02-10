<template>
  <a-modal unmount-on-close :visible="visible" :title="title" :footer="false">
    <a-form :model="formData" layout="vertical" @submit="handleSubmit">
      <a-form-item field="parentCategoryName" label="上级分类" v-if="formData.parentCategoryId && !formData.categoryId">
        <a-input v-model="formData.parentCategoryName" disabled/>
      </a-form-item>
      <a-form-item field="categoryName" label="分类名称" :rules="categoryNameRules">
        <a-input v-model="formData.categoryName"/>
      </a-form-item>
      <a-form-item field="enabled" label="是否启用" required>
        <a-switch v-model="formData.enabled"/>
      </a-form-item>
      <operations-group center>
        <a-button type="primary" html-type="submit" :loading="loading">保存</a-button>
        <a-button type="outline" @click="handleCancel">取消</a-button>
      </operations-group>
    </a-form>
  </a-modal>
</template>

<script setup lang="ts">
import OperationsGroup from "@/components/OperationsGroup/index.vue"
import type {PropType} from "vue";
import type {FieldRule, ValidatedError} from "@arco-design/web-vue";
import {ref} from "vue";
import {createCategory, updateCategory} from "@/api/category-manage";
import {Notification} from "@arco-design/web-vue";

defineProps({
  visible: {
    type: Boolean,
    required: true
  },
  title: {
    type: String,
    default: ""
  },
  formData: {
    type: Object as PropType<CategoryCreateForm>,
    default: {}
  }
})

const emit = defineEmits(["cancel"]);

const handleCancel = () => {
  emit("cancel")
}

const categoryNameRules = ref<FieldRule[]>([{required: true, message: "分类名称必填"}])
const loading = ref(false)

const handleSubmit = async ({values, errors}: {
  errors: Record<string, ValidatedError> | undefined;
  values: Record<string, any>;
}) => {
  if (loading.value) {
    return
  }
  if (errors) {
    return
  }
  loading.value = true
  try {
    if (values.categoryId) {
      await updateCategory(values.categoryId, {...values});
      Notification.success("更新成功");
    } else {
      await createCategory({...values});
      Notification.success("创建成功");
    }
    emit("cancel", true)
  } finally {
    loading.value = false
  }
}

</script>

<style scoped>

</style>