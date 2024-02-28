import {computed} from "vue";

export function useVModel(props: any, propName: string, emits: Function) {
    return computed({
        get() {
            const target = props[propName];

            if (typeof target === 'object' && target !== null) {
                return new Proxy(target, {
                    get(target, key) {
                        return Reflect.get(target, key)
                    },
                    set(target, key, value) {
                        emits("update:" + propName, {
                            ...target,
                            [key]: value
                        });
                        return true;
                    }
                });
            } else {
                // 如果 props[propName] 不是对象，直接返回该值
                return target;
            }
        },
        set(value) {
            emits("update:" + propName, value)
        }
    })
}