export interface GitRepositoryForm {
    gitRepositoryId?: number,
    url?: string,
    localPath?: string,
    username?: string,
    password?: string,
    branch?: string,
}

export interface GitRepositoryRequest {
    gitRepositoryId: number,
    url: string,
    localPath: string,
    username: string,
    password: string,
    branch: string,
}

export interface GitRepositoryResponse {
    gitRepositoryId: number,
    url: string,
    localPath: string,
    username: string,
    password: string,
    branch: string,
}