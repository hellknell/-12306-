<template>
  <p>
    <a-space>
      <a-button type="primary" @click="handleQuery()">刷新</a-button>
      <#if !readOnly><a-button type="primary" @click="onAdd">新增</a-button></#if>
    </a-space>
  </p>
  <a-table :dataSource="${domain}s"
           :columns="columns"
           :pagination="pagination"
           @change="handleTableChange"
           :loading="loading">
    <template #bodyCell="{ column,record }">
      <template v-if="column.dataIndex === 'operation'">
        <#if !readOnly>
        <a-space>
          <a-popconfirm
              title="删除后不可恢复，确认删除?"
              @confirm="onDelete(record)"
              ok-text="确认" cancel-text="取消">
            <a style="color: red">删除</a>
          </a-popconfirm>
          <a @click="onEdit(record)">编辑</a>
        </a-space>
        </#if>
      </template>
      <#list fieldList as field>
        <#if field.enums>
      <template v-else-if="column.dataIndex === '${field.nameHump}'">
        <span v-for="item in ${field.enumsConst}_ARRAY" :key="item.code">
          <span v-if="item.code === record.${field.nameHump}">
            {{item.desc}}
          </span>
        </span>
      </template>
        </#if>
      </#list>
    </template>
  </a-table>
  <#if !readOnly>
  <a-modal v-model:visible="visible" title="${tableNameCn}" @ok="handleOk"
           ok-text="确认" cancel-text="取消">
    <a-form :model="${domain}" :label-col="{span: 4}" :wrapper-col="{ span: 20 }">
      <#list fieldList as field>
        <#if field.name!="id" && field.nameHump!="createTime" && field.nameHump!="updateTime">
      <a-form-item label="${field.nameCn}">
        <#if field.enums>
        <a-select v-model:value="${domain}.${field.nameHump}">
          <a-select-option v-for="item in ${field.enumsConst}_ARRAY" :key="item.code" :value="item.code">
            {{item.desc}}
          </a-select-option>
        </a-select>
        <#elseif field.javaType=='Date'>
          <#if field.type=='time'>
        <a-time-picker v-model:value="${domain}.${field.nameHump}" valueFormat="HH:mm:ss" placeholder="请选择时间" />
          <#elseif field.type=='date'>
        <a-date-picker v-model:value="${domain}.${field.nameHump}" valueFormat="YYYY-MM-DD" placeholder="请选择日期" />
          <#else>
        <a-date-picker v-model:value="${domain}.${field.nameHump}" valueFormat="YYYY-MM-DD HH:mm:ss" show-time placeholder="请选择日期" />
          </#if>
        <#else>
        <a-input v-model:value="${domain}.${field.nameHump}" />
        </#if>
      </a-form-item>
        </#if>
      </#list>
    </a-form>
  </a-modal>
  </#if>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import request from "@/util/request";
import {notification} from "ant-design-vue";
    <#list fieldList as field>
    <#if field.enums>
    const ${field.enumsConst}_ARRAY = window.${field.enumsConst}_ARRAY;
    </#if>
    </#list>
    const visible = ref(false);
    let ${domain} = ref({
      <#list fieldList as field>
      ${field.nameHump}: undefined,
      </#list>
    });
    const ${domain}s = ref([]);
    // 分页的三个属性名是固定的
    const pagination = ref({
      total: 0,
      current: 1,
      pageSize: 10,
    });
    let loading = ref(false);
    const columns = [
    <#list fieldList as field>
      <#if field.name!="id" && field.nameHump!="createTime" && field.nameHump!="updateTime">
    {
      title: '${field.nameCn}',
      dataIndex: '${field.nameHump}',
      key: '${field.nameHump}',
    },
      </#if>
    </#list>
    <#if !readOnly>
    {
      title: '操作',
      dataIndex: 'operation'
    }
    </#if>
    ];

    <#if !readOnly>
    const onAdd = () => {
      ${domain}.value = {};
      visible.value = true;
    };
    const onEdit = (record) => {
      ${domain}.value = window.Tool.copy(record);
      visible.value = true;
    };
    
    const onDelete = (record) => {
      request.delete("/admin/${do_main}/delete/" + record.id).then((res) => {
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
      request.post("/admin/${do_main}/save", ${domain}.value).then((res) => {
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
    </#if>
    const handleQuery = (param) => {
      if (!param) {
        param = {
          pageNum: 1,
          pageSize: pagination.value.pageSize
        };
      }
      loading.value = true;
      request.get("/admin/${do_main}/query-list", {
        params: {
          pageNum: param.pageNum,
          pageSize: param.pageSize
        }
      }).then((res) => {
        loading.value = false;
        if (res.success) {
          ${domain}s.value = res.data.list;
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
