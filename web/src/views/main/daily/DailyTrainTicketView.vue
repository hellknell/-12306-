<template>
  <a-space :size="15" style="display: flex;justify-content: flex-start">
    <a-button type="primary" @click="handleQuery()">刷新</a-button>
    <a-button type="primary" @click="onAdd">新增</a-button>
  </a-space>
  <a-table :dataSource="dailyTrainTickets"
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
    </template>
  </a-table>
  <a-modal v-model:visible="visible" title="余票信息" @ok="handleOk"
           ok-text="确认" cancel-text="取消">
    <a-form :model="dailyTrainTicket" :label-col="{span: 4}" :wrapper-col="{ span: 20 }">
      <a-form-item label="日期">
        <a-date-picker v-model:value="dailyTrainTicket.date" valueFormat="YYYY-MM-DD" placeholder="请选择日期"/>
      </a-form-item>
      <a-form-item label="车次编号">
        <a-input v-model:value="dailyTrainTicket.trainCode"/>
      </a-form-item>
      <a-form-item label="出发站">
        <a-input v-model:value="dailyTrainTicket.start"/>
      </a-form-item>
      <a-form-item label="出发站拼音">
        <a-input v-model:value="dailyTrainTicket.startPinyin"/>
      </a-form-item>
      <a-form-item label="出发时间">
        <a-time-picker v-model:value="dailyTrainTicket.startTime" valueFormat="HH:mm:ss" placeholder="请选择时间"/>
      </a-form-item>
      <a-form-item label="出发站序">
        <a-input v-model:value="dailyTrainTicket.startIndex"/>
      </a-form-item>
      <a-form-item label="到达站">
        <a-input v-model:value="dailyTrainTicket.end"/>
      </a-form-item>
      <a-form-item label="到达站拼音">
        <a-input v-model:value="dailyTrainTicket.endPinyin"/>
      </a-form-item>
      <a-form-item label="到站时间">
        <a-time-picker v-model:value="dailyTrainTicket.endTime" valueFormat="HH:mm:ss" placeholder="请选择时间"/>
      </a-form-item>
      <a-form-item label="到站站序">
        <a-input v-model:value="dailyTrainTicket.endIndex"/>
      </a-form-item>
      <a-form-item label="一等座余票">
        <a-input v-model:value="dailyTrainTicket.ydz"/>
      </a-form-item>
      <a-form-item label="一等座票价">
        <a-input v-model:value="dailyTrainTicket.ydzPrice"/>
      </a-form-item>
      <a-form-item label="二等座余票">
        <a-input v-model:value="dailyTrainTicket.edz"/>
      </a-form-item>
      <a-form-item label="二等座票价">
        <a-input v-model:value="dailyTrainTicket.edzPrice"/>
      </a-form-item>
      <a-form-item label="软卧余票">
        <a-input v-model:value="dailyTrainTicket.rw"/>
      </a-form-item>
      <a-form-item label="软卧票价">
        <a-input v-model:value="dailyTrainTicket.rwPrice"/>
      </a-form-item>
      <a-form-item label="硬卧余票">
        <a-input v-model:value="dailyTrainTicket.yw"/>
      </a-form-item>
      <a-form-item label="硬卧票价">
        <a-input v-model:value="dailyTrainTicket.ywPrice"/>
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script setup>
import {onMounted, ref} from 'vue';
import request from "@/util/request";
import {notification} from "ant-design-vue";

const visible = ref(false);
let dailyTrainTicket = ref({
  id: undefined,
  date: undefined,
  trainCode: undefined,
  start: undefined,
  startPinyin: undefined,
  startTime: undefined,
  startIndex: undefined,
  end: undefined,
  endPinyin: undefined,
  endTime: undefined,
  endIndex: undefined,
  ydz: undefined,
  ydzPrice: undefined,
  edz: undefined,
  edzPrice: undefined,
  rw: undefined,
  rwPrice: undefined,
  yw: undefined,
  ywPrice: undefined,
  createTime: undefined,
  updateTime: undefined,
});
const dailyTrainTickets = ref([]);
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
    title: '出发站',
    dataIndex: 'start',
    key: 'start',
  },
  {
    title: '出发站拼音',
    dataIndex: 'startPinyin',
    key: 'startPinyin',
  },
  {
    title: '出发时间',
    dataIndex: 'startTime',
    key: 'startTime',
  },
  {
    title: '出发站序',
    dataIndex: 'startIndex',
    key: 'startIndex',
  },
  {
    title: '到达站',
    dataIndex: 'end',
    key: 'end',
  },
  {
    title: '到达站拼音',
    dataIndex: 'endPinyin',
    key: 'endPinyin',
  },
  {
    title: '到站时间',
    dataIndex: 'endTime',
    key: 'endTime',
  },
  {
    title: '到站站序',
    dataIndex: 'endIndex',
    key: 'endIndex',
  },
  {
    title: '一等座余票',
    dataIndex: 'ydz',
    key: 'ydz',
  },
  {
    title: '一等座票价',
    dataIndex: 'ydzPrice',
    key: 'ydzPrice',
  },
  {
    title: '二等座余票',
    dataIndex: 'edz',
    key: 'edz',
  },
  {
    title: '二等座票价',
    dataIndex: 'edzPrice',
    key: 'edzPrice',
  },
  {
    title: '软卧余票',
    dataIndex: 'rw',
    key: 'rw',
  },
  {
    title: '软卧票价',
    dataIndex: 'rwPrice',
    key: 'rwPrice',
  },
  {
    title: '硬卧余票',
    dataIndex: 'yw',
    key: 'yw',
  },
  {
    title: '硬卧票价',
    dataIndex: 'ywPrice',
    key: 'ywPrice',
  },
  {
    title: '操作',
    dataIndex: 'operation'
  }
];

const onAdd = () => {
  dailyTrainTicket.value = {};
  visible.value = true;
};
const onEdit = (record) => {
  dailyTrainTicket.value = JSON.parse(JSON.stringify(record));
  visible.value = true;
};

const onDelete = (record) => {
  request.delete("/business/admin/daily-train-ticket/delete/" + record.id).then((res) => {
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
  request.post("/business/admin/daily-train-ticket/save", dailyTrainTicket.value).then((res) => {
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
  request.get("/business/admin/daily-train-ticket/query-list", {
    params: {
      pageNum: param.pageNum,
      pageSize: param.pageSize
    }
  }).then((res) => {
    loading.value = false;
    if (res.success) {
      dailyTrainTickets.value = res.data.list;
      pagination.value.current = param.pageNum;
      pagination.value.total = res.content.total;
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
