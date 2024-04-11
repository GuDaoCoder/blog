import {createRouter, createWebHistory} from "vue-router";
import {adminRoutes} from './routes'
import {getToken} from "@/utils/auth";
import {closeProgress, startProgress} from '@/plugin/nprogress'

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        ...adminRoutes
    ]
});
const whiteUrls = ['/', '/login', '/home', '/post']
/**
 * 导航守卫
 */
router.beforeEach((to, from, next) => {
    startProgress()
    if (whiteUrls.indexOf(to.path) != -1) {
        next()
    } else {
        if (getToken()) {
            next()
        } else {
            next({name: 'login'})
        }
    }
})

router.afterEach(() => {
    closeProgress()
})

export default router;

