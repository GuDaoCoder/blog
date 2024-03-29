interface SearchPostBlogRequest extends PageRequest {
    title?: string
}

interface PostBlogResponse {
    postId: number,
    title: string,
    summary?: string,
    coverPictureUrl?: string,
    status: string,
    source: string,
    categoryId?: number,
    categoryName?: string,
    publishTime?: string,
    updateTime: string,
    enableComment?: boolean,
    tags?: PagePostTagItem[]
}

interface PagePostTagItem {
    tagId: number,
    tagName: string,
    color: string
}