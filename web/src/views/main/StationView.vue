<template>
  <div>
    <div style="text-align: left">
      <a-space direction="horizontal" :size="10" align="start" style="text-align: left">
        <a-button type="primary" danger @click="save">新增</a-button>
        <a-button type="primary" @click="handlePage">刷新</a-button>
      </a-space>
    </div>
    <a-table :data-source="dataSource" :columns="columns" :loading="loading" :pagination=pagination @change="onChange"
             @resizeColumn="handleResizeColumn">
      <template #bodyCell="{ column, record }">
        <template v-if="column.dataIndex === 'operation'">
          <a-space :size="15">
            <a-popconfirm title="确定要删除吗" @confirm="del(record.id)" ok-text="确认"
                          cancel-text="取消">
              <template #icon>
                <question-circle-outlined style="color: red"/>
              </template>
              <a style="color: red" href="#">删除</a>
            </a-popconfirm>
            <a-button size="small" @click="save(record)" type="primary">
              <template #icon>
                <EditOutlined/>
              </template>
            </a-button>
          </a-space>
        </template>
      </template>
    </a-table>
    <!--    <a-pagination size="small" :total="50" />-->
    <a-modal
        v-model:visible="visible"
        title="车站信息"
        @ok="handleOk"
        ok-text="确认"
        cancel-text="取消"
        keyboard
    >
      <a-form :model="station" label-align="right" :rules="rules" :label-col="{span:6}"
              :wrapper-col="{span:18,offset:0}">
        <a-form-item has-feedback label="车站名" name="name">
          <a-input v-model:value="station.name"/>
        </a-form-item>
        <a-form-item has-feedback label="车站拼音" name="namePinyin">
          <a-input v-model:value="station.namePinyin" disabled/>
        </a-form-item>
        <a-form-item has-feedback label="拼音首字母" name="namePy">
          <a-input v-model:value="station.namePy" disabled/>
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>
<script setup>
import {onMounted, reactive, ref, watch} from "vue";
import request from "@/util/request";
import {message, notification} from "ant-design-vue"
import {EditOutlined, QuestionCircleOutlined} from '@ant-design/icons-vue';
import '@/assets/js/enum'
import {pinyin} from "pinyin-pro";

const loading = ref(false)
const pagination = ref({
  total: 0,
  pageSize: 4,
  current: 1
})
const save = (data) => {
  station.value = JSON.parse(JSON.stringify(data))
  visible.value = true
}
const dataSource = ref([])
const columns = ref([
  {
    title: '车站名称',
    dataIndex: 'name',
    resizable: true
  },
  {
    title: '车站拼音',
    dataIndex: 'namePinyin',
    resizable: true
  },
  {
    title: '车站拼音首字母',
    dataIndex: 'namePy',
    resizable: true
  },
  {
    title: '操作',
    dataIndex: 'operation'
  },
])
const rules = reactive({
  name: [{required: true, message: '请输入车站名', trigger: 'blur'}],
  namePinyin: [{required: true, message: '请输入车站拼音', trigger: 'blur'}],
  namePy: [{required: true, message: '请输入车站拼音首字母', trigger: 'blur'}],
})
// const del = (record) => {
//   // request.del("/passenger/del?id=" + record.id.then(res => {
//   //
//   // }))
// }
const onChange = (page) => {
  console.log(page)
  handlePage({
    pageNum: page.current,
    pageSize: page.pageSize
  })
}
const visible = ref(false);
const station = reactive(
    {
      name: '',
      namePinyin: '',
      namePy: ''
    }
)
watch(() =>station.name, () => {
  if (station.name) {
    station.namePinyin = pinyin(station.name, {toneType: 'none'}).replaceAll(" ", "")
    station.namePy = pinyin(station.name, {pattern: 'first', toneType: 'none'}).replaceAll(" ", "")
  }
})
onMounted(() => {
  handlePage({
    pageNum: pagination.value.current,
    pageSize: pagination.value.pageSize
  })
})
const handlePage = (param) => {
  if (!param) {
    param = {
      pageNum: 1,
      pageSize: 4
    }
  }
  loading.value = true
  request.get("/admin/station/query-list", {
    params: {
      pageNum: param.pageNum,
      pageSize: param.pageSize
    }
  }).then(res => {
    loading.value = false
    if (res) {
      dataSource.value = res.data?.list
      pagination.value.current = res.data?.pageNum
      pagination.value.total = res.data?.total
    }
  })
}
// const showModal = () => {
//   visible.value = true;
// };
const handleResizeColumn = (w, col) => {
  col.width = w
}
const del = (record) => {
  request.delete("/admin/station/del" + record).then(res => {
    if (res.success) {
      message.success("删除成功")
      handlePage()
    } else {
      notification.error({description: res.message})
    }
  })
}
const handleOk = () => {
  request.post("/admin/station/save", station
  ).then(res => {
    if (res.code === '200') {
      message.success({content: "操作成功"})
      visible.value = false;
      handlePage({
        pageNum: pagination.value.current,
        pageSize: pagination.value.pageSize
      })
    } else {
      notification.error({message: res.msg})
    }
  })
}
</script>
<style scoped>
</style>