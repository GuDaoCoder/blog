export interface SearchTagForm {
    tagName?: string
}

export interface SearchTagRequest extends PageRequest {
    tagName?: string
}

export interface PortalSearchTagRequest extends PageRequest {
    tagName?: string
}

export interface TagResponse {
    tagId: number,
    tagName: string,
    enable: boolean,
    color: string,
    postCount: number,
    createTime: string,
    updateTime: string
}

export interface TagDetailResponse extends TagResponse {
    postCount: number
}