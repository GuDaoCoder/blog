import {ADMIN_LAYOUT} from './layout'
import type {RouteRaw} from "@/router/routes/types";

const HOME: RouteRaw = {
    path: '/admin',
    name: 'admin',
    component: ADMIN_LAYOUT,
    redirect: '/admin/home',
    children: [
        {
            name: 'admin-home',
            path: "/admin/home",
            component: () => import(/* webpackChunkName: "home" */ "@/views/admin/home/index.vue"),
            meta: {
                icon: 'home',
                title: '主页'
            },
        }]
}

export default HOME