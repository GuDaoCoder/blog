<template>
  <a-modal unmount-on-close :visible="visible" :title="title" fullscreen :footer="false" :closable="false">
    <div class="step-wrapper">
      <a-steps type="navigation" :current="step" class="steps">
        <a-step>编写文章</a-step>
        <a-step>设置文章</a-step>
      </a-steps>
    </div>
    <keep-alive>
      <md-edit ref="mdEditor" v-if="step ===1" :content="formData.content"/>

      <div v-else class="post-form-wrapper">
        <a-form ref="formRef" :model="formData" :rules="rules" layout="vertical">
          <a-form-item field="title" label="文章标题">
            <a-input v-model="formData.title"/>
          </a-form-item>

          <a-form-item field="categoryId" label="文章分类">
            <category-select v-model="formData.categoryId"/>
          </a-form-item>

          <a-form-item label="摘要">
            <a-textarea v-model="formData.summary" show-word-limit allow-clear auto-size :max-length="100"/>
          </a-form-item>

          <a-form-item field="tagIds" label="标签">
            <tag-select v-model="formData.tagIds"/>
          </a-form-item>

          <a-form-item field="top" label="是否置顶">
            <a-switch v-model="formData.top"/>
          </a-form-item>

          <a-form-item field="enableComment" label="是否开启评论">
            <a-switch v-model="formData.enableComment"/>
          </a-form-item>
        </a-form>
      </div>
    </keep-alive>

    <operations-group center style="margin-top: 20px">
      <a-button v-if="step === 2" type="primary" @click="handleChangeStep(1)">上一步</a-button>
      <a-button v-if="step === 1" type="primary" @click="handleChangeStep(2)">下一步</a-button>
      <a-button v-if="step === 2" type="primary" @click="handleSubmit" :loading="loading">保存</a-button>
      <a-button type="outline" @click="handleCancel">返回</a-button>
    </operations-group>
  </a-modal>
</template>

<script setup lang="ts">
import {type PropType, reactive, ref} from "vue";
import CategorySelect from "@/components/CategorySelect/index.vue"
import TagSelect from "@/components/TagSelect/index.vue"
import OperationsGroup from "@/components/OperationsGroup/index.vue";
import MdEdit from "@/components/MdEdit/index.vue";
import {Notification, type ValidatedError} from "@arco-design/web-vue";
import {createPost, updatePost} from "@/api/post-manage";

const props = defineProps({
  visible: {
    type: Boolean,
    required: true
  },
  title: {
    type: String,
    default: ""
  },
  formData: {
    type: Object as PropType<SavePostForm>,
    default: {}
  }
});

const emit = defineEmits(["cancel"]);

const rules = reactive({
  title: [{required: true, message: "文章标题必填"}],
  categoryId: [{required: true, message: "文章分类必填"}],
  tagIds: [{required: true, message: "文章标签必填"}],
  top: [{required: true, message: "是否置顶必填"}],
  enableComment: [{required: true, message: "是否开启评论必填"}],
  encrypt: [{required: true, message: "是否加密访问必填"}],
  password: [{required: true, message: "访问密码必填"}],
})
const mdEditor = ref();

const step = ref<number>(1)

const handleChangeStep = (val: number) => {
  if (val === 2) {
    props.formData.content = mdEditor.value.getValue();
  }
  step.value = val
}

const loading = ref<boolean>(false)
const formRef = ref()

const handleSubmit = () => {
  formRef.value.validate(async (errors: undefined | Record<string, ValidatedError>) => {
    if (loading.value) {
      return
    }
    if (errors) {
      return
    }
    loading.value = true
    try {
      if (props.formData.postId) {
        await updatePost(props.formData.postId, {...props.formData})
        Notification.success("更新成功");
      } else {
        await createPost({...props.formData})
        Notification.success("创建成功");
      }
      emit("cancel", true)
    } finally {
      loading.value = false
    }
  });
}

const handleCancel = () => {
  emit("cancel")
}
</script>

<style scoped lang="scss">
.step-wrapper {
  width: 600px;
  margin: 0 auto;
  margin-bottom: 20px;
}

.post-form-wrapper {
  width: 500px;
  margin: 0 auto;
}

</style>