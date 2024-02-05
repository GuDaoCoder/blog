export interface BaseRequestType {

}

export interface LoginRequestType extends BaseRequestType {
    username: string,
    password: string
}