import {createApp} from 'vue'
import router from "@/router";
import store from "@/store";
import App from '@/App.vue'
import '@/style/reset.scss'
// arco-design组件
import ArcoVue from '@arco-design/web-vue';
import ArcoVueIcon from '@arco-design/web-vue/es/icon';
import '@arco-design/web-vue/dist/arco.css';
// svg插件
import 'virtual:svg-icons-register'
// 全局组件
import globalComponent from '@/components'
// axios
import "@/request"
// 全局通用方法
import utils from "@/utils"

createApp(App)
    .use(router)
    .use(store)
    .use(ArcoVue)
    .use(ArcoVueIcon)
    .use(globalComponent)
    .use(utils)
    .mount('#app')
