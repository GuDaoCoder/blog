import '@/assets/main.scss'

import {createApp} from 'vue'
import {createPinia} from 'pinia'
import router from "@/router";
import App from '@/App.vue'
// arco-design组件
import ArcoVue from '@arco-design/web-vue';
import '@arco-design/web-vue/dist/arco.css';

createApp(App)
    .use(createPinia())
    .use(router)
    .use(ArcoVue)
    .mount('#app')
