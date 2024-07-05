<script lang="ts" setup>

import {ref} from "vue";

const props = defineProps({
  visible: {
    type: Boolean,
    required: true
  },
  log: {
    type: String,
    required: false
  }
})

const console = ref();

const emit = defineEmits(["cancel"]);
const handleCancel = () => {
  emit("cancel")
}
</script>

<template>
  <a-modal :footer="false" :visible="visible" title="监控信息" unmount-on-close width="70%" @cancel="handleCancel">
    <div ref="console" class="console">
      <p v-for="(item, index) in log.split('\n')" v-if="log" :key="index" class="log-entry">{{ item }}</p>
    </div>
  </a-modal>
</template>

<style lang="scss" scoped>
.console {
  width: 100%;
  height: 600px;
  background-color: #000;
  color: #fff;
  padding: 10px;
  overflow-y: auto;
  border-radius: 5px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);
}

.log-entry {
  margin: 0;
}
</style>