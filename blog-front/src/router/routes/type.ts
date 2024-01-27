import {defineComponent} from 'vue';

export type Component<T = any> =
    | ReturnType<typeof defineComponent>
    | (() => Promise<typeof import('*.vue')>)
    | (() => Promise<T>);

export interface RouteMeta {
    title: string,
    icon?: string
}

export interface RouteRaw {
    path: string,
    component: Component | string,
    name?: string,
    redirect?: string,
    children?: Array<RouteRaw>
    meta?: RouteMeta
}