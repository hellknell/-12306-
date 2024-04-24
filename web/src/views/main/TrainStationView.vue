<template>
  <a-space direction="horizontal" :size="10" style="display: flex;justify-content: flex-start">
    <a-button type="primary" danger @click="save">新增</a-button>
    <a-button type="primary" @click="handleQuery()">刷新</a-button>
  </a-space>
  <a-table :dataSource="trainStations"
           :columns="columns"
           :pagination="pagination"
           @change="handleTableChange"
           :loading="loading">
    <template #bodyCell="{ column,record }">
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
  <a-modal
      v-model:visible="visible"
      title="火车信息"
      @ok="handleOk"
      ok-text="确认"
      cancel-text="取消"
      keyboard
  >
    <a-form :model="trainStation" label-align="right" :label-col="{span:6}"
            :wrapper-col="{span:18,offset:0}">
      <a-form-item has-feedback label="车次编号" name="trainCode">
        <a-input v-model:value="trainStation.trainCode"/>
      </a-form-item>
      <a-form-item has-feedback label="站序" name="index">
        <a-input v-model:value="trainStation.index"/>
      </a-form-item>
      <a-form-item has-feedback label="站名拼音" name="startPinyin">
        <a-input v-model:value="trainStation.namePinyin"/>
      </a-form-item>
      <a-form-item has-feedback label="站名" name="name">
        <a-input v-model:value="trainStation.name"/>
      </a-form-item>
      <a-form-item has-feedback label="入站时间" name="inTime">
        <a-time-picker v-model:value="trainStation.inTime" value-format="HH:mm:ss"
                       :default-value="dayjs('00:00:00', 'HH:mm:ss')">
          <template #suffixIcon>
            <smile-outlined/>
          </template>
        </a-time-picker>
      </a-form-item>
      <a-form-item has-feedback label="出站时间" name="outTime">
        <a-time-picker v-model:value="trainStation.outTime" value-format="HH:mm:ss"
                       :default-value="dayjs('00:00:00', 'HH:mm:ss')">
          <template #suffixIcon>
            <smile-outlined/>
          </template>
        </a-time-picker>
      </a-form-item>
      <a-form-item has-feedback label="公里数" name="km">
        <a-input v-model:value="trainStation.km"/>
      </a-form-item>

    </a-form>
  </a-modal>
</template>
<script setup>
import {onMounted, ref,} from 'vue';
import request from "@/util/request";
import {message, notification} from "ant-design-vue";
import {EditOutlined, QuestionCircleOutlined, SmileOutlined} from "@ant-design/icons-vue";
import dayjs from "dayjs";


const visible = ref(false);
const trainStation = ref({
  id: undefined,
  trainCode: undefined,
  index: undefined,
  name: undefined,
  namePinyin: undefined,
  inTime: undefined,
  outTime: undefined,
  stopTime: undefined,
  km: undefined,
  createTime: undefined,
  updateTime: undefined,
});
const trainStations = ref([]);
// 分页的三个属性名是固定的
const pagination = ref({
  total: 0,
  current: 1,
  pageSize: 10,
});
let loading = ref(false);
const columns = [
  {
    title: '车次编号',
    dataIndex: 'trainCode',
    key: 'trainCode',
  },
  {
    title: '站序',
    dataIndex: 'index',
    key: 'index',
  },
  {
    title: '站名',
    dataIndex: 'name',
    key: 'name',
  },
  {
    title: '站名拼音',
    dataIndex: 'namePinyin',
    key: 'namePinyin',
  },
  {
    title: '进站时间',
    dataIndex: 'inTime',
    key: 'inTime',
  },
  {
    title: '出站时间',
    dataIndex: 'outTime',
    key: 'outTime',
  },
  {
    title: '停站时长',
    dataIndex: 'stopTime',
    key: 'stopTime',
  },
  {
    title: '里程（公里）',
    dataIndex: 'km',
    key: 'km',
  },
  {
    title: '操作',
    dataIndex: 'operation'
  },
];
const handleQuery = (param) => {
  if (!param) {
    param = {
      page: 1,
      size: pagination.value.pageSize
    };
  }
  loading.value = true;
  request.get("/admin/train-station/query-list", {
    params: {
      pageNum: param.page,
      pageSize: param.size
    }
  }).then((res) => {
    loading.value = false
    if (res.success) {
      trainStations.value = res.data?.list;
      pagination.value.current = param.page;
      pagination.value.total = res.data?.total;
    } else {
      notification.error({description: res.msg});
    }
  });
};

const handleTableChange = (page) => {
  pagination.value.pageSize = page.pageSize;
  handleQuery({
    page: page.current,
    size: page.pageSize
  })
};
onMounted(() => {
  handleQuery({
    page: 1,
    size: pagination.value.pageSize
  });
})
const del = (record) => {
  request.delete("/admin/train-station/del/" + record).then(res => {
    if (res.success) {
      message.success("删除成功")
      handleQuery()
    } else {
      notification.error({description: res.msg})
    }
  })
}
const save = (data) => {
  trainStation.value = JSON.parse(JSON.stringify(data))
  visible.value = true
}
const handleOk = () => {
  request.post("/admin/train-station/save", trainStation.value
  ).then(res => {
    if (res.code === '200') {
      message.success({content: "操作成功"})
      visible.value = false;
      handleQuery({
        pageNum: pagination.value.current,
        pageSize: pagination.value.pageSize
      })
      trainStation.value = {}
      handleQuery()
    } else {
      notification.error({message: res.msg})
    }
  })
}
</script>
