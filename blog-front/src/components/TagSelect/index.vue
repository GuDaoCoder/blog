<template>
  <a-select v-model="model" :loading="loading" multiple>
    <a-option v-for="option in options" :key="option.tagId" :label="option.tagName" :tag-props="{color:option.color}"
              :value="option.tagId"/>
  </a-select>
</template>

<script lang="ts" setup>
import tagApi from "@/api/tag/index"
import {onMounted, ref} from "vue";
import {useVModel} from "@/utils/useVModel";
import type {TagDetailResponse} from "@/api/tag/types";

const props = defineProps({
  modelValue: {
    type: Array<number>,
    default: []
  }
})

const emits = defineEmits(["update:modelValue"])

// loading
const loading = ref<boolean>(false)
// 选项数据
const options = ref<TagDetailResponse[]>([])

onMounted(() => {
  fetchOptions()
})

const fetchOptions = async () => {
  try {
    loading.value = true
    const {data} = await tagApi.searchTag({pageNumber: 1, pageSize: 10000})
    options.value = data.items || []
    initValues.value = options.value.map(obj => obj.tagId)
  } finally {
    loading.value = false
  }
}

const initValues = ref<Number[]>([])
const model = useVModel(props, "modelValue", emits)
</script>

<style scoped>

</style>