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
      <template v-if="column.dataIndex === 'operation'">
        <a-space>
          <a-popconfirm
              title="删除后不可恢复，确认删除?"
              @confirm="onDelete(record)"
              ok-text="确认" cancel-text="取消">
            <a style="color: red">删除</a>
          </a-popconfirm>
        </a-space>
      </template>
      <template v-if="column.dataIndex==='seatType'">
          <span v-for="item in SEAT_TYPE_ARRAY" :key="item.code">
          <span v-if="item.code === record.seatType">
            <a-tag color="success">{{ item.desc }}</a-tag>
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
const SEAT_TYPE_ARRAY = window.SEAT_TYPE_ARRAY1
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
    dataIndex: 'date'
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
    dataIndex: 'row'
  },
  {
    title: '列号',
    dataIndex: 'col'
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
  }
];
const onDelete = (record) => {
  request.delete("/member/admin/ticket/del/" + record.id).then((res) => {
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
  request.post("/member/admin/ticket/save", confirmOrder.value).then((res) => {
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
