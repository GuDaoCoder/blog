import type {RouteRaw} from "@/router/routes/types";
import {OPEN_LAYOUT} from "@/router/routes/portal/layout";

const HOME: RouteRaw = {
    path: '/',
    name: 'blog',
    component: OPEN_LAYOUT,
    redirect: '/home',
    children: [
        {
            name: 'home',
            path: "/home",
            component: () => import(/* webpackChunkName: "home" */ "@/views/portal/home/index.vue"),
        }, {
            name: 'post',
            path: "/post",
            component: () => import(/* webpackChunkName: "home" */ "@/views/portal/post/index.vue"),
        }]
}

export default HOME