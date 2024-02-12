export function dict(obj: any, ...keys: []): string {
    return keys.filter(key => obj.hasOwnProperty(key)).map(key => obj[key]).join(",") || ""
}