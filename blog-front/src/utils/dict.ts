import type {RadioOption} from "@arco-design/web-vue/es/radio/interface";

export function useDict<T extends Record<string, string>>(enumObj: T, value: string): string {
    return enumObj[value as keyof T] || "";
}

export function getRadioOptions<T extends Record<string, string>>(enumObj: T): RadioOption[] {
    return Object.keys(enumObj).map((key) => {
        return {
            label: enumObj[key as keyof T],
            value: key
        };
    });
}