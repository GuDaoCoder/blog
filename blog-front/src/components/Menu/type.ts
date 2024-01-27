export interface Menu {
    code: string,
    title: string,
    icon?: string,
    children?: Array<Menu>
}