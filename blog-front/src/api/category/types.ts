export interface QueryCategoryTreeRequest {
    categoryName?: string
}

export interface CategoryResponse {
    categoryId: number,
    categoryName: string,
    parentCategoryId: number,
    parentCategoryName: string,
    fullId: string,
    orderNo: number,
    level: number,
    enabled: boolean,
    postCount: number,
    createTime: string,
    updateTime: string
}

export interface CategoryTreeResponse extends CategoryResponse {
    children?: CategoryTreeResponse[]
}

export interface PortalQueryCategoryTreeRequest {
    categoryName?: string
}