import {ADMIN_LAYOUT} from '../base'
import type {RouteRaw} from "@/router/routes/type";

const BLOG_MANGE: RouteRaw = {
    path: '/admin/blog-manage',
    component: ADMIN_LAYOUT,
    redirect: '/admin/category-manage',
    meta: {
        icon: 'blog',
        title: '博客管理'
    },
    children: [
        {
            name: 'admin-category-manage',
            path: "/admin/category-manage",
            component: () => import(/* webpackChunkName: "category-manage" */ "@/views/admin/category-manage/index.vue"),
            meta: {
                title: '分类管理'
            }
        },
        {
            name: 'admin-tag-manage',
            path: "/admin/tag-manage",
            component: () => import(/* webpackChunkName: "tag-manage" */ "@/views/admin/tag-manage/index.vue"),
            meta: {
                title: '标签管理'
            }
        },
        {
            name: 'admin-post-manage',
            path: "/admin/post-manage",
            component: () => import(/* webpackChunkName: "post-manage" */ "@/views/admin/post-manage/index.vue"),
            meta: {
                title: '文章管理'
            }
        }]
}

export default BLOG_MANGE