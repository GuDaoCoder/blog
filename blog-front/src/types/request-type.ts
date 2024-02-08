export interface BaseRequestType {
    requestId?: string
}

export interface LoginRequestType extends BaseRequestType {
    username: string,
    password: string
}

export interface CategoryTreeRequestType extends BaseRequestType{
    categoryName?:string,
    enabled?:boolean
}