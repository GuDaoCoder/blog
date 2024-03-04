interface SearchPostForm {
    title?: string,
    status?: string,
    categoryId?: number,
    top?: boolean,
    enableComment?: boolean
}

interface PagePostDTO extends PageDTO {
    title?: string,
    status?: string,
    categoryId?: number,
    top?: boolean,
    enableComment?: boolean
}

interface PagePostVO {
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
    enableComment?: boolean,
    tags: PagePostTagItem[]
}

interface PagePostTagItem {
    tagId: number,
    tagName: string,
    color: string
}

interface SavePostForm {
    postId?: number,
    title?: string,
    summary?: string,
    content?: string,
    coverPictureUrl?: string,
    status?: string,
    categoryId?: number,
    categoryName?: string,
    tagIds?: number[],
    top?: boolean,
    enableComment?: boolean,
    publish?: boolean
}

interface CreatePostDTO {
    title?: string,
    summary?: string,
    content?: string,
    coverPictureUrl?: string,
    status?: string,
    categoryId?: number,
    tagIds?: number[],
    top?: boolean,
    enableComment?: boolean,
    publish?: boolean
}

interface CreatePostVO {
    postId: string
}

interface UpdatePostDTO {
    title?: string,
    summary?: string,
    content?: string,
    coverPictureUrl?: string,
    status?: string,
    categoryId?: number,
    tagIds?: number[],
    top?: boolean,
    enableComment?: boolean,
    publish?: boolean
}
