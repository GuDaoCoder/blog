import type {RouteRaw} from "@/router/routes/types";

const LOGIN: RouteRaw = {
    path: '/login',
    name: 'login',
    component: () => import('@/views/admin/login/index.vue')
}

export default LOGIN