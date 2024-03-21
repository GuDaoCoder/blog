<template>
  <v-md-preview :text="text" height="400px" @copy-code-success="handleCopyCodeSuccess"></v-md-preview>
</template>

<script lang="ts" setup>
import {ref} from "vue";
import {Message} from '@arco-design/web-vue';

const content = "## 简介\n" +
    "Druid是Java语言中最好的数据库连接池。Druid能够提供强大的监控和扩展功能。<!-- more -->\n" +
    "## 添加依赖\n" +
    "```xml\n" +
    "<dependency>\n" +
    "    <groupId>com.alibaba</groupId>\n" +
    "    <artifactId>druid-spring-boot-starter</artifactId>\n" +
    "    <version>1.2.8</version>\n" +
    "</dependency>\n" +
    "```\n" +
    "网上有不少教程建议的依赖是\n" +
    "```xml\n" +
    "<dependency>\n" +
    "    <groupId>com.alibaba</groupId>\n" +
    "    <artifactId>druid</artifactId>\n" +
    "    <version>1.2.8</version>\n" +
    "</dependency>\n" +
    "```";
const text = ref(content)

const handleCopyCodeSuccess = () => {
  Message.success("复制成功")
}
</script>

<style scoped>

</style>