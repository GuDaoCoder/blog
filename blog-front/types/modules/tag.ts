interface SearchTagForm {
    tagName?: string
}

interface PageTagDTO extends PageDTO {
    tagName?: string
}

interface PageTagVO {
    tagId: string,
    tagName: string,
    enable: boolean,
    color: string,
    postCount: number,
    updateTime: string
}

interface SaveTagForm {
    tagId?: string,
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
    tagId?: string
}