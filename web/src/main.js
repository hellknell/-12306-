import {createApp} from 'vue'
import App from './App.vue'
import router from './router'
import store from '@/store/index'
import Antd from 'ant-design-vue'
import dayjs from "dayjs";
import 'ant-design-vue/dist/antd.variable.min.css';
import * as Icons from '@ant-design/icons-vue'
import '@/assets/css/global.css'
import { ConfigProvider } from 'ant-design-vue';
ConfigProvider.config({
  theme: {
    primaryColor: '',
  }
});
const app = createApp(App)
import MakeitCaptcha from 'makeit-captcha'
import 'makeit-captcha/dist/captcha.min.css'
app.config.globalProperties.$baseUrl=process.env.VUE_APP_BASEURL
app.use(store).use(Antd).use(router).use(MakeitCaptcha).use(dayjs).mount('#app')
const icons=Icons
for (const i in icons){
  app.component(i,icons[i])
}
console.log(process.env.NODE_ENV)
console.log(process.env.VUE_APP_BASEURL)