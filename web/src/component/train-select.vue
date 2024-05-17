<template>
  <a-select v-model:value="trainCode" :style="'width :'+width" show-search :filterOption="filter" allow-clear @change="onChange">
    <a-select-option v-for="(item,index) in trains" :value="item.code" :key="index" :label="item.code+item.start+item.end">
      {{ item.code }} | {{ item.start }}--{{ item.end}}
    </a-select-option>
  </a-select>
</template>
<script setup>
import {defineProps, onMounted, ref, watch,defineEmits} from "vue";
import request from "@/util/request"
const emit = defineEmits(['update:modelValue'])
const props = defineProps(['modelValue','width'])
const trains = ref([])
const trainCode = ref('')
watch(() => props.modelValue, () => {
  trainCode.value = props.modelValue
}, {immediate: true})
const width =ref(props.width || '100%')
if(width.value){
  width.value=width.value+'px'
}

const filter = (input, option) => {
  return option.label.toLowerCase().indexOf(input.toLowerCase()) >= 0
}

const onChange = (value) => {
  emit('update:modelValue', value);
  let train = trains.value.filter(item => item.code === value)[0];
  if (train ===null) {
    train = {};
  }
  emit('change', train);
};
onMounted(() => {
  request.get("/business/admin/train/query-train-code").then(res => {
    if (res.success) {
      trains.value = res.data
    }
  })
})
</script>