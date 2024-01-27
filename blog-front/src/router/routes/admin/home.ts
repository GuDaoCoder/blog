import {ADMIN_LAYOUT} from '../base'
import type {RouteRaw} from "@/router/routes/type";

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