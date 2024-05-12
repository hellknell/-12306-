<template>
  <div class="job">
    <a-space :size="10" style="display: flex;justify-content: flex-start">
      <a-button type="primary" @click="handleAdd()">
        新增
      </a-button>
      <a-button type="primary" @click="handleQuery()">
        刷新
      </a-button>
    </a-space>
    <a-table :dataSource="jobs"
             :columns="columns"
             :loading="loading"
    >
      <template #bodyCell="{ column, record }">
        <template v-if="column.dataIndex === 'operation'">
          <a-space>
            <a-popconfirm
                title="手动执行会立即执行一次，确定执行？"
                ok-text="是"
                cancel-text="否"
                @confirm="handleRun(record)"
            >
              <a-button type="primary" size="small">
                手动执行
              </a-button>
            </a-popconfirm>
            <a-popconfirm
                title="确定重启？"
                ok-text="是"
                cancel-text="否"
                @confirm="handleResume(record)"
            >
              <a-button v-show="record.state === 'PAUSED' || record.state === 'ERROR'" type="primary" size="small">
                重启
              </a-button>
            </a-popconfirm>
            <a-popconfirm
                title="确定暂停？"
                ok-text="是"
                cancel-text="否"
                @confirm="handlePause(record)"
            >
              <a-button v-show="record.state === 'NORMAL' || record.state === 'BLOCKED'" type="primary" size="small">
                暂停
              </a-button>
            </a-popconfirm>
            <a-button type="primary" @click="handleEdit(record)" size="small">
              编辑
            </a-button>
            <a-popconfirm
                title="删除后不可恢复，确认删除?"
                ok-text="是"
                cancel-text="否"
                @confirm="handleDelete(record)"
            >
              <a-button type="danger" size="small">
                删除
              </a-button>
            </a-popconfirm>
          </a-space>
        </template>
      </template>
    </a-table>

    <a-modal
        title="用户"
        v-model:visible="modalVisible"
        :confirm-loading="modalLoading"
        @ok="handleModalOk"
    >
      <a-form :model="job" :label-col="{ span: 6 }" :wrapper-col="{ span: 18 }">
        <a-form-item label="类名">
          <a-input v-model:value="job.name"/>
        </a-form-item>
        <a-form-item label="描述">
          <a-input v-model:value="job.description"/>
        </a-form-item>
        <a-form-item label="分组">
          <a-input v-model:value="job.group" :disabled="!!job.state"/>
        </a-form-item>
        <a-form-item label="表达式">
          <a-input v-model:value="job.cronExpression"/>
          <div class="ant-alert ant-alert-success">
            每5秒执行一次：0/5 * * * * ?
            <br>
            每5分钟执行一次：* 0/5 * * * ?
          </div>
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script setup>
import {onMounted, ref} from 'vue';
import {notification} from 'ant-design-vue';
import request from "@/util/request";

const jobs = ref();
const loading = ref();

const columns = [{
  title: '分组',
  dataIndex: 'group',
}, {
  title: '类名',
  dataIndex: 'name',
}, {
  title: '描述',
  dataIndex: 'description',
}, {
  title: '状态',
  dataIndex: 'state',
}, {
  title: '表达式',
  dataIndex: 'cronExpression',
}, {
  title: '上次时间',
  dataIndex: 'preFireTime',
}, {
  title: '下次时间',
  dataIndex: 'nextFireTime',
}, {
  title: '操作',
  dataIndex: 'operation'
}];

const handleQuery = () => {
  loading.value = true;
  jobs.value = [];
  request.get('/batch/admin/job/query').then((res) => {
    loading.value = false;
    if (res.success) {
      jobs.value = res.data
    } else {
      notification.error({description: res.msg});
    }
  });
};
const job = ref();
job.value = {};
const modalVisible = ref(false);
const modalLoading = ref(false);
const handleModalOk = () => {
  modalLoading.value = true;
  let url = "add";
  if (job.value.state) {
    url = "reschedule";
  }
  request.post('/batch/admin/job/' + url, job.value).then((res) => {
    modalLoading.value = false;
    if (res.success) {
      modalVisible.value = false;
      notification.success({description: "保存成功！"});
      handleQuery();
    } else {
      notification.error({description:res.msg});
    }
  });
};
/**
 * 新增
 */
const handleAdd = () => {
  modalVisible.value = true;
  job.value = {
    group: 'DEFAULT'
  };
};

/**
 * 编辑
 */
const handleEdit = (record) => {
  modalVisible.value = true;
  job.value = JSON.parse(JSON.stringify(record));
};

/**
 * 删除
 */
const handleDelete = (record) => {
  request.delete('/batch/admin/job/delete', {
    name: record.name,
    group: record.group
  }).then((res) => {
    if (res.success) {
      notification.success({description: "删除成功！"});
      handleQuery();
    } else {
      notification.error({description: res.msg});
    }
  });
};

/**
 * 暂停
 */
const handlePause = (record) => {
  request.post('/batch/admin/job/pause', {
    name: record.name,
    group: record.group
  }).then((res) => {

    if (res.success) {
      notification.success({description: "暂停成功！"});
      handleQuery();
    } else {
      notification.error({description: res.msg});
    }
  });
};

/**
 * 重启
 */
const handleResume = (record) => {
  request.put('/batch/admin/job/reschedule', record).then((res) => {
    modalLoading.value = false;
    if (res.success) {
      modalVisible.value = false;
      notification.success({description: "重启成功！"});
      handleQuery();
    } else {
      notification.error({description: res.msg});
    }
  });
};

/**
 * 手动执行
 */
const handleRun = (record) => {
  request.post('/batch/admin/job/run', record).then((res) => {
    if (res.success) {
      notification.success({description: "手动执行成功！"});
    } else {
      notification.error({description: res.msg});
    }
  });
};

// const getEnumValue = (key, obj) => {
//   return Tool.getEnumValue(key, obj);
// };

onMounted(() => {
  console.log('index mounted!');
  handleQuery();
})
</script>

<style scoped>
</style>
