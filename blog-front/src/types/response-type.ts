export interface BaseResponseType<T = unknown> {
    requestId?: string,
    timestamp?: number,
    errorMsg?: string,
    data?: T
}

export interface LoginResponseType {
    token: string
}