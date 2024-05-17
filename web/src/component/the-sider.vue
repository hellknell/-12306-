<template>

  <a-menu
      v-model:selectedKeys="state.selectedKeys"
      v-model:openKeys="state.openKeys"
      mode="inline"
      :inline-collapsed="state.collapsed"
      :style="{ height: '100%', borderRight: 0 }"
  >
    <a-menu-item key="/welcome">
      <router-link to="/welcome">
        <CoffeeOutlined/>
        欢迎页
      </router-link>
    </a-menu-item>

    <a-sub-menu key="/daily" :popupOffset="10">
      <template #title>
           <span>
          <user-outlined/>
          每日数据
        </span>
      </template>
      <a-menu-item key="/daily/train-ticket">
        <router-link to="/daily/train-ticket">
          <UnorderedListOutlined/>
          每日余票查询
        </router-link>
      </a-menu-item>
      <a-menu-item key="/daily/train">
        <router-link to="/daily/train">
          <UnorderedListOutlined/>
          每日火车
        </router-link>
      </a-menu-item>
      <a-menu-item key="/daily/train-station">
        <router-link to="/daily/train-station">
          <UnorderedListOutlined/>
          每日车次
        </router-link>
      </a-menu-item>
      <a-menu-item key="/daily/train-carriage">
        <router-link to="/daily/train-carriage">
          <UnorderedListOutlined/>
          每日车厢
        </router-link>
      </a-menu-item>
      <a-menu-item key="/daily/train-seat">
      <router-link to="/daily/train-seat">
        <UnorderedListOutlined/>
        每日车座
      </router-link>
    </a-menu-item>

    </a-sub-menu>
    <a-sub-menu key="/base">
      <template #title>
        <span>
          <user-outlined/>
          火车基础数据
        </span>
      </template>
      <a-menu-item key="/base/station">
        <router-link to="/base/station">
          <user-outlined/>
          车站管理
        </router-link>
      </a-menu-item>
      <a-menu-item key="/base/train">
        <router-link to="/base/train">
          <user-outlined/>
          火车管理
        </router-link>
      </a-menu-item>
      <a-menu-item key="/base/train-station">
        <router-link to="/base/train-station">
          <user-outlined/>
          火车车站管理
        </router-link>
      </a-menu-item>
      <a-menu-item key="/base/train-carriage">
        <router-link to="/base/train-carriage">
          <user-outlined/>
          火车车厢管理
        </router-link>
      </a-menu-item>
      <a-menu-item key="/base/train-seat">
        <router-link to="/base/train-seat">
          <user-outlined/>
          火车车座管理
        </router-link>
      </a-menu-item>
    </a-sub-menu>

    <a-sub-menu key="/batch" :popupOffset="10">
      <template #title>
           <span>
          <user-outlined/>
          跑批管理
        </span>
      </template>
      <a-menu-item key="/batch/job">
        <router-link to="/batch/job">
          <UnorderedListOutlined/>
          任务管理
        </router-link>
      </a-menu-item>
    </a-sub-menu>
  </a-menu>

</template>
<script setup>
import {CoffeeOutlined, UnorderedListOutlined, UserOutlined} from '@ant-design/icons-vue';
import {reactive, watch} from 'vue';
import router from "@/router";

const state = reactive({
  collapsed: false,
  selectedKeys: [],
  openKeys: ['/base','/daily'],
  preOpenKeys: ['/'],
});
// const toggleCollapsed = () => {
//   state.collapsed = !state.collapsed;
//   state.openKeys = state.collapsed ? [] : state.preOpenKeys;
// }
watch(() => router.currentRoute.value.path, (newValue) => {
  console.log(newValue)
  state.selectedKeys = []
  state.selectedKeys.push(newValue)
}, {immediate: true})
</script>
<style>
.ant-menu-title-content {
  width: 150px !important;
}

#components-layout-demo-top-side-2 .logo {
  float: left;
  width: 120px;
  height: 31px;
  margin: 16px 24px 16px 0;
  background: rgba(255, 255, 255, 0.3);
}

.ant-row-rtl #components-layout-demo-top-side-2 .logo {
  float: right;
  margin: 16px 0 16px 24px;
}

.ant-menu-item {
  padding-left: -10px !important;
}

.site-layout-background {
  background: #fff;
}
</style>