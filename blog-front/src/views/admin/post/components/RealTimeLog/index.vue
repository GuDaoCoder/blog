<script lang="ts" setup>
import {ref} from "vue";
import {getToken} from "@/utils/auth";

defineProps({
  visible: {
    type: Boolean,
    required: true
  }
})

const logContainer = ref()
const wsUrl = `${import.meta.env.VITE_APP_BASE_WS}/admin/blog/sync?Authorization=${getToken()}`
const ws = new WebSocket(wsUrl);

ws.onerror = function () {
  console.log("ws连接发生错误");
};
//连接成功建立的回调方法
ws.onopen = function () {
}
//接收到消息的回调方法
ws.onmessage = function (event) {
  const message = event.data;
  const logEntry = document.createElement('p');
  logEntry.className = 'log-entry';
  logEntry.textContent = message;
  logContainer.value.appendChild(logEntry);
  logContainer.value.scrollTop = logContainer.value.scrollHeight;
}
//连接关闭的回调方法
ws.onclose = function () {
}

// 关闭WebSocket连接
const closeWebSocket = () => {
  if (ws.readyState === WebSocket.OPEN) {
    ws.close();
  }
}

// 在页面卸载时关闭WebSocket连接
window.addEventListener('beforeunload', function () {
  closeWebSocket();
});

const emit = defineEmits(["cancel"]);
const handleCancel = () => {
  closeWebSocket()
  emit("cancel")
}
</script>

<template>
  <a-modal :footer="false" :visible="visible" title="实时日志信息" unmount-on-close width="70%" @cancel="handleCancel">
    <div ref="logContainer" class="console"></div>
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