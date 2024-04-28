<template>

  <a-menu
      v-model:selectedKeys="state.selectedKeys"
      v-model:openKeys="state.openKeys"
      mode="inline"
      :inline-collapsed="state.collapsed"
      :style="{ height: '100%', borderRight: 0 }"
  >
    <a-sub-menu key="/" :popupOffset="10">
      <template #title>
        <span>
          <user-outlined/>
                数据管理
        </span>
      </template>
      <a-menu-item key="/welcome">
        <router-link to="/welcome">
          <CoffeeOutlined/>
          欢迎页
        </router-link>
      </a-menu-item>
      <a-menu-item key="/station">
        <router-link to="/station">
          <user-outlined/>
          车站管理
        </router-link>
      </a-menu-item>
      <a-menu-item key="/train">
        <router-link to="/train">
          <user-outlined/>
          火车管理
        </router-link>
      </a-menu-item>
      <a-menu-item key="/train-station">
        <router-link to="/train-station">
          <user-outlined/>
          火车车站管理
        </router-link>
      </a-menu-item>
      <a-menu-item key="/train-carriage">
        <router-link to="train-carriage">
          <user-outlined/>
          火车车厢管理
        </router-link>
      </a-menu-item>
      <a-menu-item key="/train-seat">
        <router-link to="/train-seat">
          <user-outlined/>
          火车车座管理
        </router-link>
      </a-menu-item>
    </a-sub-menu>

  </a-menu>

</template>
<script setup>
import {CoffeeOutlined, UserOutlined} from '@ant-design/icons-vue';
import {reactive, watch} from 'vue';
import router from "@/router";

const state = reactive({
  collapsed: false,
  selectedKeys: [],
  openKeys: ['/'],
  preOpenKeys: ['/'],
});
// const toggleCollapsed = () => {
//   state.collapsed = !state.collapsed;
//   state.openKeys = state.collapsed ? [] : state.preOpenKeys;
// };
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