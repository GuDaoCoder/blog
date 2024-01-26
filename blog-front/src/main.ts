import {createApp} from 'vue'
import router from "@/router";
import store from "@/store";
import App from '@/App.vue'
import '@/style/reset.scss'
// arco-design组件
import ArcoVue from '@arco-design/web-vue';
import '@arco-design/web-vue/dist/arco.css';
// svg插件
import 'virtual:svg-icons-register'
// 全局组件
import globalComponent from '@/components'

createApp(App)
    .use(router)
    .use(store)
    .use(ArcoVue)
    .use(globalComponent)
    .mount('#app')
