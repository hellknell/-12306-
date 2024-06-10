<template>
  <a-space :size="15" style="display: flex;justify-content: flex-start">
    <train-select v-model:value="params.code" width="180"></train-select>
    <a-button @click="batch">批量删除</a-button>
    <a-date-picker placeholder="请输入日期" value-format="YYYY-MM-DD" v-model:value="params.date"/>
    <station-select width="150" info="请输入出发站" v-model:value="params.start"/>
    <station-select width="150" info="请输入到达站" v-model:value="params.end"/>
    <a-button type="primary" @click="handleQuery()">查询</a-button>
  </a-space>
  <a-table :dataSource="dailyTrainTickets"
           :row-selection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange }"
           @resizeColumn="handleResizeColumn"
           @change="handleTableChange"
           :columns="columns"
           :pagination="pagination"
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

      <template v-if="column.dataIndex === 'ydz'">
        <div v-if="record.ydz>=0">
          <div style="text-align: center"> {{ record.ydz }}张</div>
          <div style="white-space:nowrap;text-align:center">￥ {{ record.ydzPrice }}</div>

        </div>
        <div v-else style="text-align: center">
          --
        </div>
      </template>
      <template v-if="column.dataIndex === 'start'">
        <div style="width: max-content">
          <div> {{ record.start }}</div>
          <div style="text-align: center">to</div>
          <div>{{ record.end }}</div>
        </div>
      </template>
      <template v-if="column.dataIndex === 'startTime'">
        <div style="width: max-content">
          <div> {{ record.startTime }}</div>
          <div style="text-align: center">~</div>
          <div>{{ record.endTime }}</div>
        </div>
      </template>
      <template v-if="column.dataIndex === 'edz'">
        <div v-if="record.edz>=0">
          <div style="text-align: center">{{ record.edz }}张<br/></div>
          <div style="text-align: center;;white-space: nowrap">￥ {{ record.edzPrice }}</div>
        </div>
        <div v-else style="text-align: center">
          --
        </div>
      </template>
      <template v-if="column.dataIndex === 'yw'">
        <div v-if="record.yw>=0">
          <div style="text-align: center">{{ record.yw }}张</div>
          <br/>
          <div style="text-align: center;white-space: nowrap">￥ {{ record.ywPrice }}</div>
        </div>
        <div style="text-align: center" v-else>
          --
        </div>
      </template>
      <template v-if="column.dataIndex==='time'">
        <div style="width: max-content">
          <div> {{ record.time }}</div>
          <div v-if="record.startTime<record.endTime">当日到达</div>
          <div v-else>次日到达</div>
        </div>
      </template>
      <template v-if="column.dataIndex === 'rw'">
        <div v-if="record.rw>=0">
          <div style="text-align: center">{{ record.rw }}张</div>
          <div style="white-space: nowrap;text-align: center">￥ {{ record.rwPrice }}</div>
          <br/>
        </div>
        <div style="text-align: center" v-else>
          --
        </div>
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
import TrainSelect from "@/component/train-select.vue";
import StationSelect from "@/component/station-select.vue";

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
  pageSize: 10
});
const handleResizeColumn = (w, col) => {
  col.width = w
}
let loading = ref(false);
const columns = [
  {
    title: '日期',
    dataIndex: 'date',
    key: 'date',
    width: 200,
    resizable: true
  },
  {
    title: '车次编号',
    dataIndex: 'trainCode',
    key: 'trainCode',
    resizable: true,
    width: 100
  },
  {
    title: '车站',
    dataIndex: 'start',
    key: 'start',
    width: 100,
    resizable: true
  },
  {
    title: '出发站序',
    dataIndex: 'startIndex',
    key: 'startIndex', width: 100,
    resizable: true
  },
  {
    title: '时间',
    dataIndex: 'startTime',
    width: 100,
    key: 'startTime',
    resizable: true
  },
  {
    title: '历时',
    dataIndex: 'time',
    width: 100,
    resizable: true
  },

  {
    title: '到达站序',
    dataIndex: 'endIndex',
    width: 100,
    resizable: true
  },


  {
    title: '一等座余票',
    dataIndex: 'ydz',
    width: 150,
    key: 'ydz',
    resizable: true
  },

  {
    title: '二等座余票',
    width: 150,
    dataIndex: 'edz',
    key: 'edz',
    resizable: true
  },

  {
    title: '软卧余票',
    dataIndex: 'rw',
    key: 'rw',
    resizable: true,
    width: 150,
  },

  {
    title: '硬卧余票',
    dataIndex: 'yw',
    width: 100,
    key: 'yw',
    resizable: true
  },

  {
    title: '操作',
    dataIndex: 'operation',
    width: 200
  }
];
const ids = ref([])
const selectedRowKeys = ref([]); // Check here to configure the default column
const batch = () => {
  request.delete("")
}

const onAdd = () => {
  dailyTrainTicket.value = {};
  visible.value = true;
};
const params = ref({
  date: undefined,
  code: undefined,
  start: undefined,
  end: undefined
})
const onEdit = (record) => {
  dailyTrainTicket.value = JSON.parse(JSON.stringify(record));
  visible.value = true;
};

const onDelete = (record) => {
  request.delete("/business/admin/daily-train-ticket/delete/" + record.id).then((res) => {
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
      pageSize: param.pageSize,
      trainCode: params.value.code,
      date: params.value.date,
      start: params.value.start,
      end: params.value.end
    }
  }).then((res) => {
    loading.value = false;
    if (res.success) {
      dailyTrainTickets.value = res.data.list;
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
