import axios from "axios";

export function searchBlogPosts(params: BlogSearchPostRequest) {
    return axios.get<PageResponse<BlogPostResponse>>("/blog/posts", {params})
}

export function getPostContent(postId: number) {
    return axios.get<string>(`/blog/posts/${postId}/content`)
}

export function getPostDetail(postId: number) {
    return axios.get<PostDetailResponse>(`/blog/posts/${postId}`)
}