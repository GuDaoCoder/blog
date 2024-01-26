export interface Menu {
    code: string,
    name: string,
    url?: string,
    icon?: string,
    children?: Array<Menu>
}