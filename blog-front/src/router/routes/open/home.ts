import type {RouteRaw} from "@/router/routes/type";
import {OPEN_LAYOUT} from "@/router/routes/open/layout";

const HOME: RouteRaw = {
    path: '/',
    name: 'open',
    component: OPEN_LAYOUT,
    redirect: '/home',
    children: [
        {
            name: 'home',
            path: "/home",
            component: () => import(/* webpackChunkName: "home" */ "@/views/open/home/index.vue"),
            meta: {
                icon: 'home',
                title: '主页'
            },
        }]
}

export default HOME