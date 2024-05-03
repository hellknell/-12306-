<template>

  <a-space :size="14" style="display: flex;justify-content: flex-start">
    <train-select v-model:value="params.trainCode" width="200"></train-select>
    <a-button type="primary" @click="handleQuery()">查询</a-button>
    <a-button type="primary" danger @click="onAdd">新增</a-button>
  </a-space>
  <a-table :dataSource="trainSeats"
           :columns="columns"
           :pagination="pagination"
           @change="handleTableChange"
           :loading="loading">
    <template #bodyCell="{ column, record }">
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
        <span v-for="item in SEAT_TYPE_ARRAY" :key="item">
          <span v-if="item.type === record.seatType">
            {{ item.desc }}
          </span>
        </span>
      </template>
    </template>
  </a-table>
  <a-modal v-model:visible="visible" title="座位" @ok="handleOk"
           ok-text="确认" cancel-text="取消">
    <a-form :model="trainSeat" :label-col="{span: 4}" :wrapper-col="{ span: 20 }">
      <a-form-item label="车次编号">
        <train-select v-model:value="trainSeat.trainCode"></train-select>
      </a-form-item>
      <a-form-item label="厢序">
        <a-input v-model:value="trainSeat.carriageIndex"/>
      </a-form-item>
      <a-form-item label="座位类型">
        <a-select v-model:value="trainSeat.seatType" @change="updateCols(trainSeat.seatType)">
          <a-select-option v-for="(item,index) in SEAT_TYPE_ARRAY " :key="index" :value="item.type">
            {{ item.desc }}
          </a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item label="排号">
        <a-input v-model:value="trainSeat.row" :disabled="trainSeat.seatType==='3'|| trainSeat.seatType==='4'"/>
      </a-form-item>
      <a-form-item label="列号">
        <a-select v-model:value="trainSeat.col">
          <a-select-option v-for="item in Cols" :key="item" :value="item">
            {{ item }}
          </a-select-option>
        </a-select>
      </a-form-item>

      <a-form-item label="同车厢座序">
        <a-input v-model:value="trainSeat.carriageSeatIndex"/>
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script setup>
import {onMounted, ref} from 'vue';
import request from "@/util/request";
import {notification} from "ant-design-vue";
import TrainSelect from "@/component/train-select.vue";

const visible = ref(false);
let trainSeat = ref({
  id: undefined,
  trainCode: undefined,
  carriageIndex: undefined,
  row: undefined,
  col: undefined,
  seatType: undefined,
  carriageSeatIndex: undefined,
  createTime: undefined,
  updateTime: undefined,
});
const SEAT_TYPE_ARRAY = ref([
  {
    type: "1",
    desc: "一等座"
  }, {
    type: "2",
    desc: "二等座"
  },
  {
    type: "3",
    desc: "硬卧"
  },
  {
    type: "4",
    desc: "软卧"
  }
])
const Cols = ref([])
const params = ref({
  trainCode: ""
})

const updateCols = (type) => {

  request.get("/admin/train-seat/query-seat-col/" + type).then(res => {
    if (res.success) {
      Cols.value = res.data
    }
  })
}
const trainSeats = ref([]);
// 分页的三个属性名是固定的
const pagination = ref({
  total: 0,
  current: 1,
  pageSize: 7,
});
let loading = ref(false);
const columns = [
  {
    title: '车次编号',
    dataIndex: 'trainCode',
    key: 'trainCode',
  },
  {
    title: '厢序',
    dataIndex: 'carriageIndex',
    key: 'carriageIndex',
  },
  {
    title: '排号',
    dataIndex: 'row',
    key: 'row',
  },
  {
    title: '列号',
    dataIndex: 'col',
    key: 'col',
  },
  {
    title: '座位类型',
    dataIndex: 'seatType',
    key: 'seatType',
  },
  {
    title: '同车厢座序',
    dataIndex: 'carriageSeatIndex',
    key: 'carriageSeatIndex',
  },
  {
    title: '操作',
    dataIndex: 'operation'
  }
];

const onAdd = () => {
  trainSeat.value = {};
  visible.value = true;
};

const onEdit = (record) => {
  trainSeat.value = JSON.parse(JSON.stringify(record))
  visible.value = true;
};

const onDelete = (record) => {
  request.delete("/admin/train-seat/del/" + record.id).then((res) => {
    if (res.success) {
      notification.success({description: "删除成功！"});
      handleQuery({
        page: pagination.value.current,
        size: pagination.value.pageSize,
        trainCode: params.value.trainCode
      });
    } else {
      notification.error({description: res.msg});
    }
  });
};

const handleOk = () => {
  request.post("/admin/train-seat/save", trainSeat.value).then((res) => {
    if (res.success) {
      notification.success({description: "保存成功！"});
      visible.value = false;
      handleQuery({
        page: pagination.value.current,
        size: pagination.value.pageSize
      });

    } else {
      notification.error({description: res.msg});
    }
  });
};

const handleQuery = (param) => {
  if (!param) {
    param = {
      page: 1,
      size: pagination.value.pageSize
    };
  }
  loading.value = true;
  request.get("/admin/train-seat/query-list", {
    params: {
      pageNum: param.page,
      pageSize: param.size,
      trainCode: params.value.trainCode
    }
  }).then((res) => {
    loading.value = false;
    if (res.success) {
      trainSeats.value = res.data.list;
      pagination.value.current = param.page;
      pagination.value.total = res.data.total;
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
  });
};

onMounted(() => {
  handleQuery({
    page: 1,
    size: pagination.value.pageSize
  });
});
</script>
