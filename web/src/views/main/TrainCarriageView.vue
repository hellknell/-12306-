<template>
  <a-space direction="horizontal" :size="10" style="display: flex;justify-content: flex-start">
    <train-select v-model:model-value="params.trainCode" width="200"></train-select>
    <a-button type="primary" @click="handleQuery()">查询</a-button>
    <a-button type="primary" danger @click="save">新增</a-button>
  </a-space>
  <a-table :dataSource="trainCarriages"
           :columns="columns"
           :pagination="pagination"
           @change="handleTableChange"
           :loading="loading">
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
      <template v-else-if="column.dataIndex === 'seatType'">
        <a-tag color="pink" v-if="'1'===record.seatType">一等座</a-tag>
        <a-tag color="red" v-if="'2'===record.seatType">二等座</a-tag>
        <a-tag color="orange" v-if="'3'===record.seatType">硬卧</a-tag>
        <a-tag color="green" v-if="'4'===record.seatType">软卧</a-tag>
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
    <a-form :model="trainCarriage" label-align="right" :label-col="{span:6}"
            :wrapper-col="{span:18,offset:0}">
      <a-form-item has-feedback label="车次编号" name="trainCode">
        <TrainSelect v-model:value="trainCarriage.trainCode"></TrainSelect>

      </a-form-item>
      <a-form-item has-feedback label="车厢号" name="index">
        <a-input v-model:value="trainCarriage.index"/>
      </a-form-item>
      <a-form-item has-feedback label="座位类型" name="seatType">
        <a-radio-group v-model:value="trainCarriage.seatType" :options="options">
        </a-radio-group>
      </a-form-item>
      <a-form-item has-feedback label="座位数" name="seatCount">
        <a-input v-model:value="trainCarriage.seatCount"/>
      </a-form-item>
      <a-form-item has-feedback label="排数" name="rowCount">
        <a-input v-model:value="trainCarriage.rowCount"/>
      </a-form-item>
      <a-form-item has-feedback label="列数" name="colCount">
        <a-input v-model:value="trainCarriage.colCount"/>
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script setup>
import {onMounted, ref} from 'vue';
import request from "@/util/request";
import {message, notification} from "ant-design-vue";
import {EditOutlined, QuestionCircleOutlined} from "@ant-design/icons-vue";
import TrainSelect from "@/component/train-select.vue";
const options = [{
  value: '1',
  label: '一等座',
}, {
  value: '2',
  label: '二等座',
}, {
  value: '4',
  label: '软卧',
}, {
  value: '3',
  label: '硬卧',
}]
// const SEAT_TYPE_ARRAY = window.TrainSeat_TYPE_ARRAY;
const visible = ref(false);
let trainCarriage = ref({
  id: undefined,
  trainCode: undefined,
  index: undefined,
  seatType: undefined,
  seatCount: undefined,
  rowCount: undefined,
  colCount: undefined,
  createTime: undefined,
  updateTime: undefined,
});
const trainCarriages = ref([]);
// 分页的三个属性名是固定的
const pagination = ref({
  total: 0,
  current: 1,
  pageSize: 7,
});
const params=ref({
  trainCode:""
})
let loading = ref(false);
const columns = [
  {
    title: '车次编号',
    dataIndex: 'trainCode',
    key: 'trainCode',
  },
  {
    title: '厢号',
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
  request.get("/admin/train-carriage/query-list", {
    params: {
      pageNum: param.pageNum,
      pageSize: param.pageSize,
      trainCode: params.value.trainCode
    }
  }).then((res) => {
    loading.value = false;

    if (res.success) {
      trainCarriages.value = res.data.list;
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
    pageNum: page.current,
    pageSize: page.pageSize
  });
};

onMounted(() => {
  handleQuery({
    pageNum: 1,
    pageSize: pagination.value.pageSize
  });
});
const del = (record) => {
  request.delete("/admin/train-carriage/del/" + record).then(res => {
    if (res.success) {
      message.success("删除成功")
      handleQuery()
    } else {
      notification.error({description: res.msg})
    }
  })
}
const save = (data) => {
  trainCarriage.value = JSON.parse(JSON.stringify(data))
  visible.value = true
}
const handleOk = () => {
  request.post("/admin/train-carriage/save", trainCarriage.value
  ).then(res => {
    if (res.code === '200') {
      handleQuery()
      message.success({content: "操作成功"})
      trainCarriage.value = {}


      visible.value = false;
    } else {
      notification.error({message: res.msg})
    }
  })
}
</script>
