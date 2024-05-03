<template>
  <a-layout-header class="header" style="display: flex;;">
    <div style="flex:.8">
      12306控台页面
    </div>
    <div style="flex: 5">
      <a-menu
          theme="dark"
          mode="horizontal"
          v-model:selectedKeys="selectedKeys"
          :style="{ lineHeight: '64px' }"
      >
        <a-menu-item key="/welcome">
          <router-link to="/welcome">
            欢迎页
          </router-link>
        </a-menu-item>
        <a-menu-item key="/station">
          <router-link to="/station">
            <span style="margin-left: 10px">车站管理</span>
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
          <router-link to="/train-carriage">
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

      </a-menu>
    </div>
    <div style="flex: 2;color: white;font-weight: 400;font-size: 17px;display: flex">
      <div style="flex: 2">
        <theme-translator />
        <span style="white-space: nowrap;text-overflow: ellipsis">你好! 管理员</span>
      </div>
      <div style="flex: 2">
        <router-link to="/login" @click="logout">退出系统</router-link>
      </div>

    </div>
  </a-layout-header>
</template>

<script setup>
// import store from "@/store";
// import {message} from "ant-design-vue";
import {ref, watch} from "vue";
import router from "@/router";
import {UserOutlined} from "@ant-design/icons-vue";
import ThemeTranslator from "@/component/theme-translator.vue";

const selectedKeys = ref([])
// const logout = () => {
//   store.commit("setMember", {})
//   message.success("退出成功")
// }
// const member = ref({})
watch(() => router.currentRoute.value.path, (newValue) => {
  selectedKeys.value = []
  selectedKeys.value.push(newValue)
}, {immediate: true})
// onMounted(() => {
//   member.value = JSON.parse(localStorage?.getItem("member"))
// })

</script>

<style scoped>

</style>