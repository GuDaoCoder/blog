interface SearchTagForm {
    tagName?: string
}

interface SearchTagRequest extends PageRequest {
    tagName?: string
}

interface TagResponse {
    tagId: number,
    tagName: string,
    enable: boolean,
    color: string,
    postCount: number,
    createTime: string,
    updateTime: string
}

interface SaveTagForm {
    tagId?: number,
    tagName?: string,
    color: string,
    enable: boolean
}

interface CreateTagRequest {
    tagName: string,
    color: string,
    enable: boolean
}

interface UpdateTagRequest {
    tagName: string,
    color: string,
    enable: boolean
}