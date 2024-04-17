import axios from "axios";

const prefix = "/admin/posts";

export function searchAdminPosts(params: AdminSearchPostRequest) {
    return axios.get<PageResponse<AdminPostResponse>>(prefix, {params})
}

export function publishPost(postId: number) {
    return axios.patch(`${prefix}/${postId}/publish`)
}

export function removePost(postId: number) {
    return axios.patch(`${prefix}/${postId}/remove`)
}

export function getPostContent(postId: number) {
    return axios.get<string>(`${prefix}/${postId}/content`)
}

export function syncPost() {
    return axios.get(`${prefix}/sync`)
}

export function updatePostCoverPicture(postId: number, coverPictureUrl: string) {
    return axios.patch(`${prefix}/${postId}/updateCoverPicture`, {coverPictureUrl})
}