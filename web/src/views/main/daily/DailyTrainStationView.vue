<template>
  <a-space :size="15" style="display: flex;justify-content: flex-start">
    <a-button type="primary" @click="handleQuery()">刷新</a-button>
    <a-button type="primary" @click="onAdd">新增</a-button>
  </a-space>
  <a-table :dataSource="dailyTrainStations"
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
  <a-modal v-model:visible="visible" title="每日车站" @ok="handleOk"
           ok-text="确认" cancel-text="取消">
    <a-form :model="dailyTrainStation" :label-col="{span: 4}" :wrapper-col="{ span: 20 }">
      <a-form-item label="日期">
        <a-date-picker v-model:value="dailyTrainStation.date" valueFormat="YYYY-MM-DD" placeholder="请选择日期" />
      </a-form-item>
      <a-form-item label="车次编号">
        <a-input v-model:value="dailyTrainStation.trainCode" />
      </a-form-item>
      <a-form-item label="站序">
        <a-input v-model:value="dailyTrainStation.index" />
      </a-form-item>
      <a-form-item label="站名">
        <a-input v-model:value="dailyTrainStation.name" />
      </a-form-item>
      <a-form-item label="站名拼音">
        <a-input v-model:value="dailyTrainStation.namePinyin" />
      </a-form-item>
      <a-form-item label="进站时间">
        <a-time-picker v-model:value="dailyTrainStation.inTime" valueFormat="HH:mm:ss" placeholder="请选择时间" />
      </a-form-item>
      <a-form-item label="出站时间">
        <a-time-picker v-model:value="dailyTrainStation.outTime" valueFormat="HH:mm:ss" placeholder="请选择时间" />
      </a-form-item>
      <a-form-item label="停站时长">
        <a-time-picker v-model:value="dailyTrainStation.stopTime" valueFormat="HH:mm:ss" placeholder="请选择时间" />
      </a-form-item>
      <a-form-item label="里程（公里）">
        <a-input v-model:value="dailyTrainStation.km" />
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import request from "@/util/request";
import {notification} from "ant-design-vue";
    const visible = ref(false);
    let dailyTrainStation = ref({
      id: undefined,
      date: undefined,
      trainCode: undefined,
      index: undefined,
      name: undefined,
      namePinyin: undefined,
      inTime: undefined,
      outTime: undefined,
      stopTime: undefined,
      km: undefined,
      createTime: undefined,
      updateTime: undefined,
    });
    const dailyTrainStations = ref([]);
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
      title: '站序',
      dataIndex: 'index',
      key: 'index',
    },
    {
      title: '站名',
      dataIndex: 'name',
      key: 'name',
    },
    {
      title: '站名拼音',
      dataIndex: 'namePinyin',
      key: 'namePinyin',
    },
    {
      title: '进站时间',
      dataIndex: 'inTime',
      key: 'inTime',
    },
    {
      title: '出站时间',
      dataIndex: 'outTime',
      key: 'outTime',
    },
    {
      title: '停站时长',
      dataIndex: 'stopTime',
      key: 'stopTime',
    },
    {
      title: '里程（公里）',
      dataIndex: 'km',
      key: 'km',
    },
    {
      title: '操作',
      dataIndex: 'operation'
    }
    ];

    const onAdd = () => {
      dailyTrainStation.value = {};
      visible.value = true;
    };
    const onEdit = (record) => {
      dailyTrainStation.value = JSON.parse(JSON.stringify(record));
      visible.value = true;
    };
    
    const onDelete = (record) => {
      request.delete("/business/admin/daily-train-station/delete/" + record.id).then((res) => {
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
      request.post("/business/admin/daily-train-station/save", dailyTrainStation.value).then((res) => {
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
      request.get("/business/admin/daily-train-station/query-list", {
        params: {
          pageNum: param.pageNum,
          pageSize: param.pageSize
        }
      }).then((res) => {
        loading.value = false;
        if (res.success) {
          dailyTrainStations.value = res.data.list;
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
