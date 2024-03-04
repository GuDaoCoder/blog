interface SearchTagForm {
    tagName?: string
}

interface PageTagDTO extends PageDTO {
    tagName?: string
}

interface PageTagVO {
    tagId: number,
    tagName: string,
    enable: boolean,
    color: string,
    postCount: number,
    updateTime: string
}

interface SaveTagForm {
    tagId?: number,
    tagName?: string,
    color?: string,
    enable?: boolean
}

interface TagDTO {
    tagName?: string,
    color?: string,
    enable?: boolean
}

interface CreateTagVO {
    tagId?: number
}