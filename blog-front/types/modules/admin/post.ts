interface AdminSearchPostForm {
    title?: string,
    status?: string,
    categoryId?: number,
    top?: boolean,
    enableComment?: boolean
}

interface AdminSearchPostRequest extends PageRequest {
    title?: string,
    status?: string,
    categoryId?: number,
    top?: boolean,
    enableComment?: boolean
}

interface AdminPostResponse {
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
    tags: AdminPostTagItem[]
}

interface AdminPostTagItem {
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