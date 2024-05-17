<template>

  <a-space :size="15" style="display: flex;justify-content: flex-start">
    <train-select v-model:value="params.code" width="100"></train-select>
    <a-date-picker placeholder="请输入日期" v-model:value="params.date"/>
    <a-button type="primary" @click="handleQuery()">查询</a-button>
    <a-button type="primary" @click="gen">生成每日车次信息</a-button>
  </a-space>
  <a-table :dataSource="dailyTrains"
           :columns="columns"
           :pagination="pagination"
           @change="handleTableChange"
           :loading="loading">
    <template #bodyCell="{ column,record }">
      <template v-if="column.dataIndex === 'operation'">
        <a-space>
          <a-popconfirm
              title="删除后不可恢复，确认删除?"
              @confirm="onDelete(record)"
              ok-text="确认" cancel-text="取消">
            <a style="color: red">删除</a>
          </a-popconfirm>
          <a @click="onEdit(record)">编辑</a>
        </a-space>
      </template>
      <template v-else-if="column.dataIndex === 'type'">
        <span v-for="item in TRAIN_TYPE_ARRAY" :key="item.code">
          <span v-if="item.code === record.type">
            {{ item.desc }}
          </span>
        </span>
      </template>
    </template>
  </a-table>
  <a-modal v-model:visible="visible" title="每日车次" @ok="handleOk"
           ok-text="确认" cancel-text="取消">
    <a-form :model="dailyTrain" :label-col="{span: 4}" :wrapper-col="{ span: 20 }">
      <a-form-item label="日期">
        <a-date-picker v-model:value="dailyTrain.date" value-format="YYYY-MM-DD" placeholder="请选择日期"/>
      </a-form-item>
      <a-form-item label="车次编号">
        <train-select v-model:value="dailyTrain.code" @change="onChange"></train-select>
      </a-form-item>
      <a-form-item label="车次类型">
        <a-select v-model:value="dailyTrain.type">
          <a-select-option v-for="item in TRAIN_TYPE_ARRAY" :key="item.code" :value="item.code">
            {{ item.desc }}
          </a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item label="始发站">
        <a-input v-model:value="dailyTrain.start"/>
      </a-form-item>
      <a-form-item label="始发站拼音">
        <a-input v-model:value="dailyTrain.startPinyin" disabled/>
      </a-form-item>
      <a-form-item label="出发时间">
        <a-time-picker v-model:value="dailyTrain.startTime" value-format="HH:mm:ss" placeholder="请选择时间"/>
      </a-form-item>
      <a-form-item label="终点站">
        <a-input v-model:value="dailyTrain.end"/>
      </a-form-item>
      <a-form-item label="终点站拼音">
        <a-input v-model:value="dailyTrain.endPinyin" disabled/>
      </a-form-item>
      <a-form-item label="到站时间">
        <a-time-picker v-model:value="dailyTrain.endTime" value-format="HH:mm:ss" placeholder="请选择时间"/>
      </a-form-item>
    </a-form>
  </a-modal>
  <a-modal v-model:visible="genVisible" title="生成车次" @ok="handleGenDailyOk"
           :confirm-loading="genDailyLoading" ok-text="确认" cancel-text="取消">
    <a-form :model="genDaily" :label-col="{span: 4}" :wrapper-col="{ span: 20 }">
      <a-form-item label="日期">
        <a-date-picker v-model:value="genDaily.date" value-format="YYYY-MM-DD" placeholder="请选择日期"/>
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script setup>
import {onMounted, ref} from 'vue';
import request from "@/util/request";
import {notification} from "ant-design-vue";
import TrainSelect from "@/component/train-select.vue";
import dayjs from "dayjs";

const TRAIN_TYPE_ARRAY = ref([{
  code: "0",
  desc: "高铁",

}, {
  code: "1",
  desc: "动车",
},
  {
    code: "2",
    desc: "普快",
  }

])
const genVisible = ref(false)
const visible = ref(false);
const genDailyLoading = ref(false);
let dailyTrain = ref({
  id: undefined,
  date: undefined,
  code: undefined,
  type: undefined,
  start: undefined,
  startPinyin: undefined,
  startTime: undefined,
  end: undefined,
  endPinyin: undefined,
  endTime: undefined,
  createTime: undefined,
  updateTime: undefined,
});
const genDaily = ref({
  date: undefined
})
const dailyTrains = ref([]);
// 分页的三个属性名是固定的
const pagination = ref({
  total: 0,
  current: 1,
  pageSize: 10,
});
const params = ref({
  code: null,
  date: null
})
let loading = ref(false);
const columns = [
  {
    title: '日期',
    dataIndex: 'date',
    key: 'date',
  },
  {
    title: '车次编号',
    dataIndex: 'code',
    key: 'code',
  },
  {
    title: '车次类型',
    dataIndex: 'type',
    key: 'type',
  },
  {
    title: '始发站',
    dataIndex: 'start',
    key: 'start',
  },
  {
    title: '始发站拼音',
    dataIndex: 'startPinyin',
    key: 'startPinyin',
  },
  {
    title: '出发时间',
    dataIndex: 'startTime',
    key: 'startTime',
  },
  {
    title: '终点站',
    dataIndex: 'end',
    key: 'end',
  },
  {
    title: '终点站拼音',
    dataIndex: 'endPinyin',
    key: 'endPinyin',
  },
  {
    title: '到站时间',
    dataIndex: 'endTime',
    key: 'endTime',
  },
  {
    title: '操作',
    dataIndex: 'operation'
  }
];

const onAdd = () => {
  dailyTrain.value = {};
  visible.value = true;
};
const onEdit = (record) => {
  visible.value = true;
  dailyTrain.value = JSON.parse(JSON.stringify(record))

};

const onDelete = (record) => {
  Object.assign(dailyTrain, record);
  request.delete("/business/admin/daily-train/delete/" + record.id).then((res) => {
    if (res.success) {
      notification.success({description: "删除成功！"});
      handleQuery({
        pageNum: pagination.value.current,
        pageSize: pagination.value.pageSize,
      });
    } else {
      notification.error({description: res.msg});
    }
  })
}
const handleOk = () => {
  request.post("/business/admin/daily-train/save", dailyTrain.value).then((res) => {
    if (res.success) {
      notification.success({description: "保存成功！"});
      visible.value = false;
      handleQuery({
        pageNum: pagination.value.current,
        pageSize: pagination.value.pageSize
      })
    } else {
      notification.error({description: res.msg});
    }
  });
};
const onChange = (train) => {
  console.log(train)
  let t = JSON.parse(JSON.stringify(train))
  delete t.id;
  Object.assign(dailyTrain.value, t);
}
const handleQuery = (param) => {
  if (!param) {
    param = {
      pageNum: 1,
      pageSize: pagination.value.pageSize
    }
  }
  loading.value = true;
  if (params.value.date) {
    var date1 = dayjs(params.value.date, "YYYY-MM-DD")
  }
  request.get("/business/admin/daily-train/query-list", {
    params: {
      pageNum: param.pageNum,
      pageSize: param.pageSize,
      code: params.value.code,
      date: date1
    }
  }).then((res) => {
    loading.value = false;
    if (res.success) {
      dailyTrains.value = res.data.list;
      pagination.value.current = param.pageNum;
      pagination.value.total = res.data.total;
    } else {
      notification.error({description: res.msg});
    }
  });
};
const gen = () => {
  genVisible.value = true
}
const handleTableChange = (page) => {
  pagination.value.pageSize = page.pageSize;
  handleQuery({
    pageNum: page.current,
    pageSize: page.pageSize
  })
}
const handleGenDailyOk = () => {
  genDailyLoading.value = true;
  request.get("/business/admin/daily-train/generate/" + genDaily.value.date).then(res => {
    genDailyLoading.value = false;
    if (res.success) {
      notification.success({description: "生成成功！"});
      genVisible.value = false
      handleQuery({
        pageNum: pagination.value.current,
        pageSize: pagination.value.pageSize
      })
    }

  })
}
onMounted(() => {
  handleQuery({
    pageNum: 1,
    pageSize: pagination.value.pageSize
  });
});
</script>
