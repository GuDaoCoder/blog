import axios from "axios";

export function pagePost(params: PagePostDTO) {
    return axios.get<PageResponse<PagePostVO>>("/admin/post", {params})
}

export function createPost(data: CreatePostDTO) {
    return axios.post<CreatePostVO>("/admin/post", data)
}

export function updatePost(postId: number, data: UpdatePostDTO) {
    return axios.put("/admin/post/" + postId, data)
}

export function publishPost(postId: number) {
    return axios.put(`/admin/post/${postId}/publish`)
}

export function unpublished(postId: number) {
    return axios.put(`/admin/post/${postId}/unpublished`)
}

export function moveRecycleBin(postId: number) {
    return axios.put(`/admin/post/${postId}/moveRecycleBin`)
}

export function getPostContent(postId: number) {
    return axios.get<string>(`/admin/post/${postId}/content`)
}