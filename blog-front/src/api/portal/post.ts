import axios from "axios";

const prefix = "/portal/posts";

export function searchBlogPosts(params: BlogSearchPostRequest) {
    return axios.get<PageResponse<BlogPostResponse>>(prefix, {params})
}

export function getPostContent(postId: number) {
    return axios.get<string>(`${prefix}/${postId}/content`)
}

export function getPostDetail(postId: number) {
    return axios.get<PostDetailResponse>(`${prefix}/${postId}`)
}