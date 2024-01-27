import {createRouter, createWebHistory} from "vue-router";
import type {RouteRecordRaw} from "vue-router";
import Home from "@/views/admin/home/index.vue";

// vue项目自带路由
const routes: Array<RouteRecordRaw> = [
    {
        path: '/admin',
        component: () => import("@/layout/admin/index.vue"),
        redirect: '/admin/home',
        children: [
            {
                name: 'admin-home',
                path: "/admin/home",
                component: () => import(/* webpackChunkName: "home" */ "@/views/admin/home/index.vue")
            },
            {
                name: 'admin-category-manage',
                path: "/admin/category-manage",
                component: () => import(/* webpackChunkName: "category-manage" */ "@/views/admin/category-manage/index.vue")
            },
            {
                name: 'admin-tag-manage',
                path: "/admin/tag-manage",
                component: () => import(/* webpackChunkName: "tag-manage" */ "@/views/admin/tag-manage/index.vue")
            },
            {
                name: 'admin-post-manage',
                path: "/admin/post-manage",
                component: () => import(/* webpackChunkName: "post-manage" */ "@/views/admin/post-manage/index.vue")
            }]
    }
];

const routers = [...routes];

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: routers
});

export default router;

