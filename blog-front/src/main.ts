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
import VMdPreview from '@kangc/v-md-editor/lib/preview';
import '@kangc/v-md-editor/lib/style/preview.css';
import vuepressTheme from '@kangc/v-md-editor/lib/theme/vuepress.js';
import '@kangc/v-md-editor/lib/theme/style/vuepress.css';
import createCopyCodePlugin from '@kangc/v-md-editor/lib/plugins/copy-code/index';
import '@kangc/v-md-editor/lib/plugins/copy-code/copy-code.css';
import createLineNumbertPlugin from '@kangc/v-md-editor/lib/plugins/line-number/index';
import Prism from 'prismjs';

VMdPreview
    // 主题
    .use(vuepressTheme, {Prism})
    // 复制按钮
    .use(createCopyCodePlugin())
    // 行号
    .use(createLineNumbertPlugin());

createApp(App)
    .use(VMdPreview)
    .use(router)
    .use(store)
    .use(ArcoVue)
    .use(ArcoVueIcon)
    .use(globalComponent)
    .use(utils)
    .mount('#app')
