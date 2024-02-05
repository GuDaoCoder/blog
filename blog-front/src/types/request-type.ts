export interface BaseRequestType {
    requestId?: string
}

export interface LoginRequestType extends BaseRequestType {
    username: string,
    password: string
}