<template>
  <div :size="14" style="display: flex;justify-content: flex-start">
    <a-space>
      <train-select v-model:value="params.code" width="100"></train-select>
      <a-date-picker placeholder="请输入日期" value-format="YYYY-MM-DD" v-model:value="params.date"/>
      <a-button type="primary" @click="handleQuery()">查询</a-button>
    </a-space>
  </div>
  <a-table :dataSource="confirmOrders"
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
      <template v-else-if="column.dataIndex === 'status'">
        <span v-for="item in ORDERSTATUS" :key="item.code">
           <a-tag color="success" v-if="item.code === record.status">{{ item.desc }}</a-tag>
        </span>
      </template>
      <template v-else-if="column.dataIndex==='tickets'">
        <div style="text-align: center">
          <a-button size="middle" type="primary" style="background-color:rebeccapurple;outline: none"
                    @click="onDetail(record.tickets)">显示详情
          </a-button>
        </div>
      </template>
    </template>
  </a-table>
  <a-modal v-model:visible="visible" title="确认订单"
           ok-text="确认" cancel-text="取消">
    <a-form :model="confirmOrder" :label-col="{span: 4}" :wrapper-col="{ span: 20 }">
      <a-form-item label="会员id">
        <a-input v-model:value="confirmOrder.memberId"/>
      </a-form-item>
      <a-form-item label="日期">
        <a-date-picker v-model:value="confirmOrder.date" valueFormat="YYYY-MM-DD" placeholder="请选择日期"/>
      </a-form-item>
      <a-form-item label="车次编号">
        <a-input v-model:value="confirmOrder.trainCode"/>
      </a-form-item>
      <a-form-item label="出发站">
        <a-input v-model:value="confirmOrder.start"/>
      </a-form-item>
      <a-form-item label="到达站">
        <a-input v-model:value="confirmOrder.end"/>
      </a-form-item>
      <a-form-item label="余票ID">
        <a-input v-model:value="confirmOrder.dailyTrainTicketId"/>
      </a-form-item>
      <a-form-item label="车票">
        <a-input v-model:value="confirmOrder.tickets"/>
      </a-form-item>
      <a-form-item label="订单状态">
        <a-select v-model:value="confirmOrder.status">
          <a-select-option v-for="item in ORDERSTATUS" :key="item.code" :value="item.code">
            {{ item.desc }}
          </a-select-option>
        </a-select>
      </a-form-item>
    </a-form>
  </a-modal>
  <a-modal v-model:visible="isVisible" title="车票详情" style="width: 60%">
    <a-row class="order-tickets-header">
      <a-col :offset="3" :span="3">乘客</a-col>
      <a-col :span="7">身份证</a-col>
      <a-col :span="3">票种</a-col>
      <a-col :span="5">座位类型</a-col>
    </a-row>
    <a-row class="order-tickets-row" v-for="ticket in tickets" :key="ticket.passengerId">
      <a-col :offset="3"  :span="3">{{ ticket.passengerName }}</a-col>
      <a-col :span="7">{{ ticket.passengerIdCard }}</a-col>
      <a-col :span="3">
                      <span v-for="item in PASSENGER_TYPE_ARRAY" :key="item.code">
                        <span v-if="item.code === ticket.passengerType">
                          {{ item.desc }}
                        </span>
                      </span>
      </a-col>
      <a-col :span="5">
                      <span v-for="item in seatTypes" :key="item.code">
                        <span v-if="item.code === ticket.seatTypeCode">
                          {{ item.desc }}({{ ticket.carriageIndex }}车{{ ticket.row }}{{ ticket.col }})
                        </span>
                      </span>
      </a-col>
    </a-row>
  </a-modal>
</template>

<script setup>
import {onMounted, ref} from 'vue';
import request from "@/util/request";
import {notification} from "ant-design-vue";
import TrainSelect from "@/component/train-select.vue";

const PASSENGER_TYPE_ARRAY = window.PASSAGER_TYPE_ARRAY
const seatTypes = SEAT_TYPE_ARRAY1
const ORDERSTATUS = window.OrderStatusEnum;
const visible = ref(false);
const isVisible = ref(false)
const tickets = ref([])
let confirmOrder = ref({
  id: undefined,
  memberId: undefined,
  date: undefined,
  trainCode: undefined,
  start: undefined,
  end: undefined,
  dailyTrainTicketId: undefined,
  tickets: undefined,
  status: undefined,
  createTime: undefined,
  updateTime: undefined,
});
const params = ref({
  code: undefined,
  date: undefined
});
const confirmOrders = ref([]);
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
    title: '到达站',
    dataIndex: 'end',
    key: 'end',
  },
  {
    title: '余票ID',
    dataIndex: 'dailyTrainTicketId',
    key: 'dailyTrainTicketId',
  },
  {
    title: '车票',
    dataIndex: 'tickets',
    key: 'tickets',
  },
  {
    title: '订单状态',
    dataIndex: 'status',
    key: 'status',
  },
  {
    title: '操作',
    dataIndex: 'operation'
  }
];


const onEdit = (record) => {
  confirmOrder.value = Tool.copy(record);
  visible.value = true;
};

const onDelete = (record) => {
  request.delete("/business/admin/confirm-order/del/" + record.id).then((res) => {
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

const handleQuery = (param) => {
  if (!param) {
    param = {
      pageNum: 1,
      pageSize: pagination.value.pageSize
    };
  }
  loading.value = true;
  request.get("/business/admin/confirm-order/query-list", {
    params: {
      pageNum: param.pageNum,
      pageSize: param.pageSize,
      code: params.value.code,
      date: params.value.date
    }
  }).then((res) => {
    loading.value = false;
    if (res.success) {
      confirmOrders.value = res.data.list;
      pagination.value.current = param.pageNum;
      pagination.value.total = res.data.total;
    } else {
      notification.error({description: res.msg});
    }
  });
};
const onDetail = (record) => {
  isVisible.value = !isVisible.value
  tickets.value = JSON.parse(JSON.parse(JSON.stringify(record)))

}
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
<style scoped>
.order-tickets-header {
  background-color: cornflowerblue;
  border: solid 1px cornflowerblue;
  color: white;
  font-size: 20px;
  padding: 5px 0;
  margin: 0 auto;
}

.order-tickets-row {
  border: solid 1px cornflowerblue;
  border-top: none;
  vertical-align: middle;
  line-height: 30px;
  font-size: 17px;
}
</style>
