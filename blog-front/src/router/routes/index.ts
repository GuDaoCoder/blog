import type {RouteRecordNormalized} from "vue-router";

const adminModules = import.meta.glob('./admin/*.ts', {eager: true});
const openModules = import.meta.glob('./portal/*.ts', {eager: true});

function formatModules(_modules: any, result: RouteRecordNormalized[]) {
    Object.keys(_modules).forEach((key) => {
        const defaultModule = _modules[key].default;
        if (!defaultModule) return;
        const moduleList = Array.isArray(defaultModule)
            ? [...defaultModule]
            : [defaultModule];
        result.push(...moduleList);
    });
    return result;
}

export const adminRoutes: RouteRecordNormalized[] = [
    ...formatModules(adminModules, []),
    ...formatModules(openModules, [])
];