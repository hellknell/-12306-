<template>
  <a-layout style="min-height: 100vh">
    <TheHeader>
    </TheHeader>
    <a-layout style="margin-top:10px">

      <a-layout-sider v-model:collapsed="collapsed" collapsible
                      style="background: #fff;height: calc(90vh - 10px);overflow: scroll">
        <TheSider/>
      </a-layout-sider>
      <a-layout style="padding: 0 18px 18px;">
        <div style="width: min-content;height:min-content">
          <a-button type="primary" @click="() => (collapsed = !collapsed)">
            <MenuUnfoldOutlined
                v-if="collapsed"
                class="trigger"
            />
            <MenuFoldOutlined v-else class="trigger"/>
          </a-button>
        </div>

        <div style="text-align: left">

        </div>

        <!--        <a-breadcrumb style="margin: 0 auto">-->
        <!--          <a-breadcrumb-item>Home</a-breadcrumb-item>-->
        <!--          <a-breadcrumb-item>List</a-breadcrumb-item>-->
        <!--          <a-breadcrumb-item>App</a-breadcrumb-item>-->
        <!--        </a-breadcrumb>-->
        <a-layout-content
            :style="{ background: '#fff', padding: '10px', margin: 0, maxHeight: '86vh' ,overflow:'scroll'}"
        >
          <router-view/>
        </a-layout-content>
      </a-layout>
    </a-layout>
  </a-layout>
</template>
<script setup>
import TheHeader from '@/component/the-header.vue'
import TheSider from '@/component/the-sider.vue'
import {ref} from "vue";
import {MenuFoldOutlined, MenuUnfoldOutlined} from "@ant-design/icons-vue";

const collapsed = ref(false)

// const toggleCollapsed = () => {
//   state.collapsed =!state.collapsed
//   state.openKeys = state.collapsed ? [] : state.preOpenKeys
//
// }

</script>



<style>

#components-layout-demo-top-side-2 .logo {
  float: left;
  width: 120px;
  height: 31px;
  margin: 16px 24px 16px 0;
  background: rgba(255, 255, 255, 0.3);
}

.ant-layout-sider-children {
  display: flex;
  justify-content: center;
}

.ant-layout-sider-collapsed {
  max-width: 24px !important;
}

.ant-menu-inline-collapsed {
  max-width: 24px !important;
}

.ant-row-rtl #components-layout-demo-top-side-2 .logo {
  float: right;
  margin: 16px 0 16px 24px;
}

.folded {

}

.site-layout-background {
  background: #fff;
}
//响应式
@media (max-width: 768px) {
  .ant-layout-sider-children {
    display: flex;
    justify-content: center;
  }
}

</style>