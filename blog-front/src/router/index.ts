import {createRouter, createWebHistory} from "vue-router";
import {adminRoutes} from './routes'
import {getToken} from "@/utils/auth";
import {startProgress, closeProgress} from '@/plugin/nprogress'

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        ...adminRoutes
    ]
});

/**
 * 导航守卫
 */
router.beforeEach((to, from, next) => {
    startProgress()
    if (to.name !== "login" && !getToken()) {
        next({name: 'login'})
    } else {
        next()
    }
})

router.afterEach(() => {
    closeProgress()
})

export default router;

