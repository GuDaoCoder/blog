import type {TagResponse} from "@/api/tag/types";

export interface QueryPostForm {
    title?: string,
    status?: string,
    categoryId?: number,
    top?: boolean,
    enableComment?: boolean
}

export interface PostQueryRequest extends PageRequest {
    title?: string,
    status?: string,
    categoryId?: number,
    top?: boolean,
    enableComment?: boolean
}

export interface PostResponse {
    postId: number,
    title: string,
    summary: string,
    coverPictureUrl: string,
    status: string,
    source: string,
    categoryId: number,
    categoryName: string,
    publishTime: string,
    updateTime: string,
    top: boolean,
    enableComment: boolean,
    tags: TagResponse[]
}

export interface PostDetailResponse extends PostResponse {
    content?: string
}

export interface PortalQueryPostRequest extends PageRequest {
    title?: string
}