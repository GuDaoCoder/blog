import {createRouter, createWebHistory} from "vue-router";
import {adminRoutes} from './routes'
import {getToken} from "@/utils/auth";


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
    if (to.name !== "login" && !getToken()) {
        next({name: 'login'})
    } else {
        next()
    }
})

export default router;

