import {createApp} from 'vue'
import {createPinia} from 'pinia'
import router from "@/router";
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
    .use(createPinia())
    .use(router)
    .use(ArcoVue)
    .use(globalComponent)
    .mount('#app')
