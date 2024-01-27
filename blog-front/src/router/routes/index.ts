const modules = import.meta.glob('./admin/*.ts', {eager: true});
export const adminRoutes = Object.keys(modules).map(key => modules[key].default);