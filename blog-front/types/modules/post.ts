interface SearchPostForm {
    title?: string,
    status?: string,
    categoryId?: string,
    encrypt?: boolean,
    top?: boolean,
    enableComment?: boolean
}

interface PagePostDTO extends PageDTO {
    title?: string,
    status?: string,
    categoryId?: string,
    encrypt?: boolean,
    top?: boolean,
    enableComment?: boolean
}

interface PagePostVO {
    postId: string,
    title: string,
    summary?: string,
    coverPictureUrl?: string,
    status: string,
    source: string,
    categoryId: string,
    categoryName: string,
    publishTime: string,
    updateTime: string,
    top: boolean,
    enableComment: boolean,
    encrypt: boolean
    password?: string,
    tags?: PagePostTagItem[]
}

interface PagePostTagItem {
    tagName?: string,
    color?: string
}