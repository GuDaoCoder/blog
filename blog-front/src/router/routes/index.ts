import type {RouteRaw} from "@/router/routes/type";

const modules = import.meta.glob('./admin/*.ts', {eager: true});
export const adminRoutes:Array<RouteRaw> = Object.keys(modules).map(key => modules[key].default);