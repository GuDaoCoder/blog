<script lang="ts" setup>
import type {FileItem} from "@arco-design/web-vue";
import {Message} from "@arco-design/web-vue";
import postApi from "@/api/post/index"

const props = defineProps({
  visible: {
    type: Boolean,
    required: true
  },
  postId: {
    type: Number,
    required: true
  }
})

const emit = defineEmits(["cancel"]);

const handleSuccessUpload = async (fileItem: FileItem) => {
  const coverPictureUrl = fileItem.response.data.url;
  await postApi.updatePostCoverPicture(props.postId, coverPictureUrl);
  Message.success('封面上传成功');
  handleCancel()
}

const handleCancel = () => {
  emit("cancel")
}

</script>

<template>
  <a-modal :footer="false" :visible="visible" title="设置封面" unmount-on-close @cancel="handleCancel">
    <a-upload :limit="1" action="/api/admin/oss/upload" draggable with-credentials
              @success="handleSuccessUpload"/>
  </a-modal>
</template>

<style lang="scss" scoped>

</style>