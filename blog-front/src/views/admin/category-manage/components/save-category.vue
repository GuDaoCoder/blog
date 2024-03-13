<template>
  <a-modal :footer="false" :title="title" :visible="visible" unmount-on-close>
    <a-form :model="formData" :rules="formRules" layout="vertical" @submit="handleSubmit">
      <a-form-item v-if=" formData.parentCategoryId && formData.parentCategoryId != 0"
                   field="parentCategoryName" label="上级分类">
        <a-input v-model="formData.parentCategoryName" disabled/>
      </a-form-item>
      <a-form-item field="categoryName" label="分类名称">
        <a-input v-model="formData.categoryName"/>
      </a-form-item>
      <a-form-item field="enabled" label="是否启用" required>
        <a-switch v-model="formData.enabled"/>
      </a-form-item>
      <operations-group center>
        <a-button :loading="loading" html-type="submit" type="primary">保存</a-button>
        <a-button type="outline" @click="handleCancel">取消</a-button>
      </operations-group>
    </a-form>
  </a-modal>
</template>

<script lang="ts" setup>
import OperationsGroup from "@/components/OperationsGroup/index.vue"
import type {PropType} from "vue";
import {reactive, ref} from "vue";
import type {ValidatedError} from "@arco-design/web-vue";
import {Notification} from "@arco-design/web-vue";
import {createCategory, updateCategory} from "@/api/admin/category-manage";

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
    type: Object as PropType<SaveCategoryForm>,
    default: {}
  }
})

const emit = defineEmits(["cancel"]);

const handleCancel = () => {
  emit("cancel")
}

const formRules = reactive({
  categoryName: [{required: true, message: "分类名称必填"}],
  enabled: [{required: true, message: "是否启用必填"}]
})

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
      await updateCategory(values.categoryId, values as UpdateCategoryRequest);
      Notification.success("更新成功");
    } else {
      await createCategory(values as CreateCategoryRequest);
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