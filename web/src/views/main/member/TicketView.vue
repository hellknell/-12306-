<template>
  <div :size="14" style="display: flex;justify-content: flex-start">
    <a-button type="primary" @click="handleQuery()">刷新</a-button>
  </div>
  <a-table :dataSource="tickets"
           :columns="columns"
           :pagination="pagination"
           @change="handleTableChange"
           :loading="loading">
    <template #bodyCell="{ column,record }">
      <template v-if="column.dataIndex==='seatType'">
          <span v-for="item in SEAT_TYPE_ARRAY" :key="item.code">
          <span v-if="item.code === record.seatType">
            <a-tag color="warning">{{ item.desc }}</a-tag>
          </span>
          </span>
      </template>
    </template>
  </a-table>
</template>

<script setup>
import {onMounted, ref} from 'vue';
import request from "@/util/request";
import {notification} from "ant-design-vue";

const tickets = ref([])
const visible = ref(false);
const SEAT_TYPE_ARRAY = window.TrainSeat_TYPE_ARRAY;
// 分页的三个属性名是固定的
const pagination = ref({
  total: 0,
  current: 1,
  pageSize: 10
});
let loading = ref(false);
const columns = [
  {
    title: '会员id',
    dataIndex: 'memberId',
    key: 'memberId',
  },
  {
    title: '乘客id',
    dataIndex: 'passengerId',
    key: 'pas',
  }, {
    title: '乘客姓名',
    dataIndex: 'passengerName'
  },
  {
    title: '日期',
    dataIndex: 'trainDate'
  },
  {
    title: '车次编号',
    dataIndex: 'trainCode',
    key: 'trainCode',
  },
  {
    title: '车座类型',
    dataIndex: 'seatType'
  },
  {
    title: '厢序',
    dataIndex: 'carriageIndex'
  },
  {
    title: '排号',
    dataIndex: 'seatRow'
  },
  {
    title: '列号',
    dataIndex: 'seatCol'
  },

  {
    title: '出发站',
    dataIndex: 'start',
    key: 'start',
  },
  {
    title: '出发时间',
    dataIndex: 'startTime'
  },
  {
    title: '到达站',
    dataIndex: 'end',
    key: 'end',
  },
  {
    title: '到达时间',
    dataIndex: 'endTime'
  },

];

const handleQuery = (param) => {
  if (!param) {
    param = {
      pageNum: 1,
      pageSize: pagination.value.pageSize
    };
  }
  loading.value = true;
  request.get("/member/admin/ticket/query-list", {
    params: {
      pageNum: param.pageNum,
      pageSize: param.pageSize
    }
  }).then((res) => {
    loading.value = false;
    if (res.success) {
      tickets.value = res.data.list;
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
