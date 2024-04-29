<template>
  <a-select v-model:value="trainCode" show-search :filterOption="filter" allow-clear @change="change">
    <a-select-option v-for="(item,index) in trains" :value="item.code" :key="index" :label="item.code+item.start+item.end">
      {{ item.code }} | {{ item.start }}--{{ item.end}}
    </a-select-option>
  </a-select>
</template>
<script setup>
import {defineProps, onMounted, ref, watch,defineEmits} from "vue";
import request from "@/util/request"
const emit = defineEmits(['update:modelValue'])
const props = defineProps(['modelValue'])
const trains = ref([])
const trainCode = ref('')
watch(() => props.modelValue, () => {
  trainCode.value = props.modelValue
}, {immediate: true})
const filter = (input, option) => {
  return option.label.toLowerCase().indexOf(input.toLowerCase()) >= 0
}
const change=()=>{
  emit('update:modelValue',trainCode.value)
}
onMounted(() => {
  request.get("/admin/train/query-train-code").then(res => {
    if (res.success) {
      trains.value = res.data
    }
  })
})
</script>