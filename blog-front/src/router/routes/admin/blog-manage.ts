import {ADMIN_LAYOUT} from './layout'
import type {RouteRaw} from "@/router/routes/type";

const BLOG_MANGE: RouteRaw = {
    path: '/admin/blog-manage',
    name: 'admin-blog-manage',
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
            name: 'admin-post',
            path: "/admin/post",
            component: () => import(/* webpackChunkName: "post" */ "@/views/admin/post/index.vue"),
            meta: {
                title: '文章管理'
            }
        }]
}

export default BLOG_MANGE