import {ADMIN_LAYOUT} from '../base'

const BLOG_MANGE = {
    path: '/admin/blog-manage',
    component: ADMIN_LAYOUT,
    redirect: '/admin/category-manage',
    children: [
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

export default BLOG_MANGE