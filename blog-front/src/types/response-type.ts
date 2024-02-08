export interface BaseResponseType<T = unknown> {
    requestId?: string,
    timestamp?: number,
    errorMsg?: string,
    data?: T
}

export interface LoginResponseType {
    token: string
}

export interface CategoryTreeResponseType {
    id: string,
    label: string,
    parentId: string,
    data?: CategoryTreeDataResponseType,
    children?: Array<CategoryTreeResponseType>
}

export interface CategoryTreeDataResponseType {
    categoryId: string,
    categoryName: string,
    parentId: string,
    fullId: string,
    orderNo: number,
    level: number
}