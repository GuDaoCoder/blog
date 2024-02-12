interface BaseVO<T> {
    requestId?: string,
    timestamp?: number,
    errorMsg?: string,
    data?: T
}

interface PageDTO {
    pageNumber: number,
    pageSize: number
}

interface PageVO<T> {
    pageNumber: number,
    pageSize: number,
    total: number,
    items?: T[]
}

interface PaginationType {
    pageNumber: number,
    pageSize: number,
    total: number,
}