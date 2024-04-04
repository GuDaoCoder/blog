import {ADMIN_LAYOUT} from './layout'
import type {RouteRaw} from "@/router/routes/type";

const BLOG_MANGE: RouteRaw = {
    path: '/admin/blog',
    name: 'admin-blog',
    component: ADMIN_LAYOUT,
    redirect: '/admin/category',
    meta: {
        icon: 'blog',
        title: '博客管理'
    },
    children: [
        {
            name: 'admin-category',
            path: "/admin/category",
            component: () => import(/* webpackChunkName: "category" */ "@/views/admin/category/index.vue"),
            meta: {
                title: '分类管理'
            }
        },
        {
            name: 'admin-tag',
            path: "/admin/tag",
            component: () => import(/* webpackChunkName: "tag" */ "@/views/admin/tag/index.vue"),
            meta: {
                title: '标签管理'
            }
        },
        {
            name: 'admin-post',
            path: "/admin/post",
            component: () => import(/* webpackChunkName: "post" */ "@/views/admin/post/index.vue"),
            meta: {
                title: '文章管理'
            }
        }]
}

export default BLOG_MANGE