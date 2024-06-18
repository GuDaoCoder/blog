export interface AdminUserForm {
    userId?: number,
    username?: string,
    password?: string,
    nickName?: string,
    email?: string,
    phone?: string,
    sex?: string,
    avatar?: string,
    lastLoginIp?: string,
    lastLoginTime?: string,
}

export interface AdminUserRequest {
    userId?: number,
    username?: string,
    password?: string,
    nickName?: string,
    email?: string,
    phone?: string,
    sex?: string,
}

export interface AdminUserResponse {
    userId: number,
    username: string,
    password: string,
    nickName: string,
    email: string,
    phone: string,
    sex: string,
    avatar: string,
    lastLoginIp: string,
    lastLoginTime: string,
}