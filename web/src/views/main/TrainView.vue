<template>
  <p>
    <a-space style="display: flex;justify-content:flex-start">
      <a-button type="primary" @click="handleQuery()">刷新</a-button>
      <a-button type="primary" danger @click="visible=true">新增</a-button>
    </a-space>
  </p>
  <a-table :dataSource="trains"
           :columns="columns"
           :pagination="pagination"
           @change="handleTableChange"
           :loading="loading">
    <template #bodyCell="{ column, record }">
      <template v-if="column.dataIndex === 'operation'">
      </template>
      <template v-else-if="column.dataIndex === 'type'">
        <span v-for="item in TRAIN_TYPE_ARRAY" :key="item.code">
          <span v-if="item.code === record.type">
            {{ item.desc }}
          </span>
        </span>
      </template>
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
    <a-form :model="train" label-align="right" :rules="rules" :label-col="{span:6}"
            :wrapper-col="{span:18,offset:0}">
      <a-form-item has-feedback label="车次编号" name="code">
        <a-input v-model:value="train.code"/>
      </a-form-item>
      <a-form-item has-feedback label="车次类型" name="type">
        <a-input v-model:value="train.type"/>
      </a-form-item>
      <a-form-item has-feedback label="始发站" name="start">
        <a-input v-model:value="train.start"/>
      </a-form-item>
      <a-form-item has-feedback label="始发站拼音" name="startPinyin">
        <a-input v-model:value="train.startPinyin"/>
      </a-form-item>
      <a-form-item has-feedback label="终点站" name="end">
        <a-input v-model:value="train.end"/>
      </a-form-item>
      <a-form-item has-feedback label="终点站拼音" name="endPinyin">
        <a-input v-model:value="train.endPinyin"/>
      </a-form-item>
      <a-form-item has-feedback label="出发时间" name="startTime">
        <a-input v-model:value="train.startTime"/>
      </a-form-item>
      <a-form-item has-feedback label="到站时间" name="endTime">
        <a-input v-model:value="train.endTime"/>
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script setup>
import {onMounted, ref} from 'vue';
import request from "@/util/request";
import {message, notification} from "ant-design-vue";
import {EditOutlined, QuestionCircleOutlined} from "@ant-design/icons-vue";

const TRAIN_TYPE_ARRAY = window.TRAIN_TYPE_ARRAY;
const visible = ref(false);
let train = ref({
  id: undefined,
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
const trains = ref([]);
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
  request.get("/admin/train/query-list", {
    params: {
      pageNum: param.page,
      pageSize: param.size
    }
  }).then((res) => {
    loading.value = false
    if (res.success) {
      trains.value = res.data?.list;
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
  });
};

onMounted(() => {
  handleQuery({
    page: 1,
    size: pagination.value.pageSize
  });
})

const del = (record) => {
  request.delete("/admin/train/del/" + record).then(res => {
    if (res.success) {
      message.success("删除成功")
      handleQuery()
    } else {
      notification.error({description: res.msg})
    }
  })

}
const save = (data) => {
  train.value = JSON.parse(JSON.stringify(data))
  visible.value = true
}
const handleOk = () => {

  request.post("/admin/train/save", train.value
  ).then(res => {
    if (res.code === '200') {
      message.success({content: "操作成功"})
      visible.value = false;

      handleQuery({
        pageNum: pagination.value.current,
        pageSize: pagination.value.pageSize
      })
      train.value = {}
      handleQuery()
    } else {
      notification.error({message: res.msg})
    }
  })

}
</script>
