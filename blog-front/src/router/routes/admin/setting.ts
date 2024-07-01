import {ADMIN_LAYOUT} from './layout';
import type {RouteRaw} from "@/router/routes/types";

const SYSTEM_SETTING: RouteRaw = {
    path: '/admin/setting',
    name: 'admin-setting',
    component: ADMIN_LAYOUT,
    redirect: '/admin/blog-setting',
    meta: {
        icon: 'portal',
        title: '系统设置',
    },
    children: [
        {
            name: "admin-task",
            path: "/admin/task",
            component: () => import(/* webpackChunkName: "task" */ "@/views/admin/task/index.vue"),
            meta: {
                title: "任务管理"
            }
        },
        {
            name: 'blog-setting',
            path: "/admin/blog-setting",
            component: () => import(/* webpackChunkName: "site" */ "@/views/admin/blog-setting/index.vue"),
            meta: {
                title: "博客设置"
            }
        }
    ]
}

export default SYSTEM_SETTING