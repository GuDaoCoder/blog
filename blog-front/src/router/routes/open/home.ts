import type {RouteRaw} from "@/router/routes/type";
import {OPEN_LAYOUT} from "@/router/routes/open/layout";

const HOME: RouteRaw = {
    path: '/',
    name: 'blog',
    component: OPEN_LAYOUT,
    redirect: '/home',
    children: [
        {
            name: 'home',
            path: "/home",
            component: () => import(/* webpackChunkName: "home" */ "@/views/open/home/index.vue"),
        }, {
            name: 'post',
            path: "/post",
            component: () => import(/* webpackChunkName: "home" */ "@/views/open/post/index.vue"),
        }]
}

export default HOME