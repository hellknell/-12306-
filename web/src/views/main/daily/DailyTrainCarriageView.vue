<template>
  <a-space :size="15" style="display: flex;justify-content: flex-start">
    <train-select v-model:value="params.code" width="100"></train-select>
    <a-date-picker placeholder="请输入日期" value-format="YYYY-MM-DD" v-model:value="params.date"/>
    <a-button type="primary" @click="handleQuery()">查询</a-button>
  </a-space>
  <a-table :dataSource="dailyTrainCarriages"
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
      <template v-else-if="column.dataIndex === 'seatType'">
        <span v-for="item in SEAT_TYPE_ARRAY" :key="item.code">
          <span v-if="item.code === record.seatType">
            {{ item.desc }}
          </span>
        </span>
      </template>
    </template>

  </a-table>
  <a-modal v-model:visible="visible" title="每日车厢" @ok="handleOk"
           ok-text="确认" cancel-text="取消">
    <a-form :model="dailyTrainCarriage" :label-col="{span: 4}" :wrapper-col="{ span: 20 }">
      <a-form-item label="日期">
        <a-date-picker v-model:value="dailyTrainCarriage.date" valueFormat="YYYY-MM-DD" placeholder="请选择日期"/>
      </a-form-item>
      <a-form-item label="车次编号">
        <a-input v-model:value="dailyTrainCarriage.trainCode"/>
      </a-form-item>
      <a-form-item label="箱序">
        <a-input v-model:value="dailyTrainCarriage.index"/>
      </a-form-item>
      <a-form-item label="座位类型">
        <a-select v-model:value="dailyTrainCarriage.seatType">
          <a-select-option v-for="item in SEAT_TYPE_ARRAY" :key="item.code" :value="item.code">
            {{ item.desc }}
          </a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item label="座位数">
        <a-input v-model:value="dailyTrainCarriage.seatCount"/>
      </a-form-item>
      <a-form-item label="排数">
        <a-input v-model:value="dailyTrainCarriage.rowCount"/>
      </a-form-item>
      <a-form-item label="列数">
        <a-input v-model:value="dailyTrainCarriage.colCount"/>
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script setup>
import {onMounted, ref} from 'vue';
import request from "@/util/request";
import {notification} from "ant-design-vue";
import TrainSelect from "@/component/train-select.vue";

const SEAT_TYPE_ARRAY = [{
  code: "1",
  desc: "一等座"
}, {
  code: "2",
  desc: "二等座"
}, {
  code: "3",
  desc: "硬卧"
}, {
  code: "4",
  desc: "软卧"
}]

const params = ref({
  code: undefined,
  date: undefined,
})
const visible = ref(false);
let dailyTrainCarriage = ref({
  id: undefined,
  date: undefined,
  trainCode: undefined,
  rowCount: undefined,
  index: undefined,
  seatType: undefined,
  seatCount: undefined,
  colCount: undefined,
  createTime: undefined,
  updateTime: undefined,
});
const dailyTrainCarriages = ref([]);
// 分页的三个属性名是固定的
const pagination = ref({
  total: 0,
  current: 1,
  pageSize: 10,
});
let loading = ref(false);
const columns = [
  {
    title: '日期',
    dataIndex: 'date',
    key: 'date',
  },
  {
    title: '车次编号',
    dataIndex: 'trainCode',
    key: 'trainCode',
  },
  {
    title: '箱序',
    dataIndex: 'index',
    key: 'index',
  },
  {
    title: '座位类型',
    dataIndex: 'seatType',
    key: 'seatType',
  },
  {
    title: '座位数',
    dataIndex: 'seatCount',
    key: 'seatCount',
  },
  {
    title: '排数',
    dataIndex: 'rowCount',
    key: 'rowCount',
  },
  {
    title: '列数',
    dataIndex: 'colCount',
    key: 'colCount',
  },
  {
    title: '操作',
    dataIndex: 'operation'
  }
];
const onEdit = (record) => {
  dailyTrainCarriage.value = JSON.parse(JSON.stringify(record));
  visible.value = true;
};

const onDelete = (record) => {
  request.delete("/business/admin/daily-train-carriage/delete/" + record.id).then((res) => {
    if (res.success) {
      notification.success({description: "删除成功！"});
      handleQuery({
        page: pagination.value.current,
        size: pagination.value.pageSize,
      });
    } else {
      notification.error({description: res.msg});
    }
  })
}
const handleOk = () => {
  request.post("/business/admin/daily-train-carriage/save", dailyTrainCarriage.value).then((res) => {
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
const handleQuery = (param) => {
  if (!param) {
    param = {
      pageNum: 1,
      pageSize: pagination.value.pageSize
    };
  }
  loading.value = true;
  request.get("/business/admin/daily-train-carriage/query-list", {
    params: {
      pageNum: param.pageNum,
      pageSize: param.pageSize,
      trainCode: params.value.code,
      date: params.value.date
    }
  }).then((res) => {
    loading.value = false;
    if (res.success) {
      dailyTrainCarriages.value = res.data.list;
      pagination.value.current = param.pageNum;
      pagination.value.total = res.data.total;
    } else {
      notification.error({description: res.msg});
    }
  });
};

const handleTableChange = (page) => {
  pagination.value.pageSize = page.pageSize;
  handleQuery({
    pageNum: page.current,
    pageSize: page.pageSize
  })
}
onMounted(() => {
  handleQuery({
    pageNum: 1,
    pageSize: pagination.value.pageSize
  });
});
</script>
