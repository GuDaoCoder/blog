export interface Category {
    categoryId: string,
    categoryName: string,
    parentId: string,
    fullId: string,
    orderNo: number,
    level: number,
    enabled: boolean,
    updateTime: string,
    children: Category[]
}