interface BaseResponse<T = unknown> {
    requestId?: string,
    timestamp?: number,
    errorMsg?: string,
    data?: T
}

interface PageRequest {
    pageNumber: number,
    pageSize: number
}

interface PageResponse<T> {
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