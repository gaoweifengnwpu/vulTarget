import {createApp} from 'vue'
import App from './App.vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import VueCookies from 'vue3-cookies'
import axios from 'axios'
import route from "./route.js";
import Vue3Storage from "vue3-storage";

import VueQuillEditor from 'vue-quill-editor'

// require styles
import 'quill/dist/quill.core.css'
import 'quill/dist/quill.snow.css'
import 'quill/dist/quill.bubble.css'

const app = createApp(App)


// 全局配置要前置
// axios.defaults.withCredentials = true;
// 挂载一个自定义属性$http

app.config.globalProperties.$http = axios
app.config.globalProperties.$cookies = VueCookies
app.config.globalProperties.$store = Vue3Storage



app.use(VueCookies).use(route).use(ElementPlus).use(Vue3Storage).use(VueQuillEditor).mount('#app')


export {app}