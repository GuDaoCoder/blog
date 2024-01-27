import {ADMIN_LAYOUT} from '../base'

const HOME = {
    path: '/admin',
    component: ADMIN_LAYOUT,
    redirect: '/admin/home',
    children: [
        {
            name: 'admin-home',
            path: "/admin/home",
            component: () => import(/* webpackChunkName: "home" */ "@/views/admin/home/index.vue")
        }]
}

export default HOME