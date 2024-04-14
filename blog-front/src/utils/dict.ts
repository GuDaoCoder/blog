export function dictLabel(obj: any, ...keys: []): string {
    return keys.filter(key => obj.hasOwnProperty(key)).map(key => obj[key]).join(",") || ""
}

export function useDict<T extends Record<string, string>>(enumObj: T, value: string): string {
    return enumObj[value as keyof T] || "";
}