interface BlogSearchPostRequest extends PageRequest {
    title?: string
}

interface BlogPostResponse {
    postId?: number,
    title?: string,
    summary?: string,
    coverPictureUrl?: string,
    status?: string,
    source?: string,
    categoryId?: number,
    categoryName?: string,
    publishTime?: string,
    updateTime: string,
    enableComment?: boolean,
    tags?: BlogPostTagItem[]
}

interface PostDetailResponse extends BlogPostResponse {
    content?: string
}

interface BlogPostTagItem {
    tagId: number,
    tagName: string,
    color: string
}