import {createRouter, createWebHistory} from "vue-router";
import type {RouteRecordRaw} from "vue-router";
import Home from "@/views/Home.vue";

// vue项目自带路由
const routes: Array<RouteRecordRaw> = [
    {
        path: "/",
        name: "Home",
        component: Home
    },
    {
        path: "/about",
        name: "About",
        component: () =>
            import(/* webpackChunkName: "about" */ "@/views/About.vue")
    }
];

const routers = [...routes];

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: routers
});

export default router;

