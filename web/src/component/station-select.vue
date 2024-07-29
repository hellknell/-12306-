<template>
  <a-select v-model:value="name" :style="'width:'+width" show-search :filterOption="filter" allow-clear
            :placeholder="info" @change="onChange">
    <a-select-option v-for="(item,index) in stations" :value="item.name" :key="index"
                     :label="item.name+item.namePinyin+item.namePy">
      {{ item.name }} | {{ item.namePinyin }}--{{ item.namePy }}
    </a-select-option>
  </a-select>
</template>
<script setup>
import {defineEmits, defineProps, onMounted, ref, watch} from "vue";
import request from "@/util/request"

const emit = defineEmits(['update:modelValue'])
const props = defineProps(['info', 'modelValue', 'width'])
const stations = ref([])
const name = ref('')
const info = ref('')
watch(() => props.modelValue, () => {
  name.value = props.modelValue
}, {immediate: true})
watch(() => props.info, () => {
  info.value = props.info
}, {immediate: true})
const width = ref(props.width || '100%')

if (width.value) {
  width.value = width.value + 'px'
}
const filter = (input, option) => {
  return option.label.toLowerCase().indexOf(input.toLowerCase()) >= 0
}
const onChange = (value) => {
  emit('update:modelValue', value);
  let train = stations.value.filter(item => item.code === value)[0];
  if (train === null) {
    train = {};
  }
  emit('change', train);
};
onMounted(() => {
  let station = SESSION.get(SESSION_ALL_STATION) || [];
  console.log('读取到了缓存:', stations)
  if (Tool.isNotEmpty(station)) {
    stations.value = station;
    console.log(stations.value)
  } else {
    request.get("/business/admin/station/query-station").then(res => {
      if (res.success) {
        stations.value = res.data
        SESSION.set(SESSION_ALL_STATION, res.data)
      }
    })
  }
})
</script>