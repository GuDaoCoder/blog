<template>
  <a-modal unmount-on-close :visible="visible" :title="title" :footer="false" :closable="false">
    <a-form :model="formData" layout="vertical" @submit="handleSubmit">
      <a-form-item field="tagName" label="分类名称" :rules="tagNameRules">
        <a-input v-model="formData.tagName"/>
      </a-form-item>
      <a-form-item field="color" label="颜色" :rules="colorRules">
        <input type="color" v-model="formData.color"/>
      </a-form-item>
      <a-form-item field="enable" label="是否启用" required>
        <a-switch v-model="formData.enable"/>
      </a-form-item>
      <operations-group center>
        <a-button type="primary" html-type="submit" :loading="loading">保存</a-button>
        <a-button type="outline" @click="handleCancel">取消</a-button>
      </operations-group>
    </a-form>
  </a-modal>
</template>

<script setup lang="ts">
import {type PropType, ref} from "vue";
import OperationsGroup from "@/components/OperationsGroup/index.vue";
import {type FieldRule, type ValidatedError, Notification} from "@arco-design/web-vue";
import {createTag, updateTag} from "@/api/tag-manage";

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
    type: Object as PropType<SaveTagForm>,
    default: {}
  }
})

const emit = defineEmits(["cancel"]);

const loading = ref<boolean>(false)

const tagNameRules = ref<FieldRule[]>([
  {
    required: true,
    message: "标签名称必填"
  }
])

const colorRules = ref<FieldRule[]>([
  {
    required: true,
    message: "颜色必填"
  }
])

const handleCancel = () => {
  emit("cancel")
}

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
    if (values.tagId) {
      await updateTag(values.tagId, {...values})
      Notification.success("更新成功");
    } else {
      await createTag({...values})
      Notification.success("创建成功");
    }
    emit("cancel", true)
  } finally {
    loading.value = false;
  }
}

</script>

<style scoped>
.button-group {
  border-top: 1px solid var(--color-neutral-3)
}
</style>