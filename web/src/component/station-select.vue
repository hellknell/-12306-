<template>
  <a-select v-model:value="name" show-search :filterOption="filter" allow-clear @change="change">
    <a-select-option v-for="(item,index) in stations" :value="item.name" :key="index" :label="item.name+item.namePinyin+item.namePy">
      {{ item.name }} | {{ item.namePinyin}}--{{ item.namePy}}
    </a-select-option>
  </a-select>
</template>
<script setup>
import {defineProps, onMounted, ref, watch,defineEmits} from "vue";
import request from "@/util/request"
const emit = defineEmits(['update:modelValue'])
const props = defineProps(['modelValue'])
const stations = ref([])
const name = ref('')
watch(() => props.modelValue, () => {
  name.value = props.modelValue
}, {immediate: true})
const filter = (input, option) => {
  return option.label.toLowerCase().indexOf(input.toLowerCase()) >= 0
}
const change=()=>{
  emit('update:modelValue',name.value)
}
onMounted(() => {
  request.get("/admin/station/query-station").then(res => {
    if (res.success) {
      stations.value = res.data
    }
  })
})
</script>