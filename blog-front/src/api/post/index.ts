import axios from "axios";
import type {PortalQueryPostRequest, PostDetailResponse, PostQueryRequest, PostResponse} from "@/api/post/types";

const ADMIN_PREFIX = "/api/admin/posts";

const PORTAL_PREFIX = "/api/portal/posts";

export const queryPost = (params: PostQueryRequest) => {
    return axios.get<PageResponse<PostResponse>>(ADMIN_PREFIX, {params})
}

export const publishPost = (postId: number) => {
    return axios.patch<void>(`${ADMIN_PREFIX}/${postId}/publish`)
}

export const removePost = (postId: number) => {
    return axios.patch<void>(`${ADMIN_PREFIX}/${postId}/remove`)
}

export const syncPost = () => {
    return axios.get(`${ADMIN_PREFIX}/sync`)
}

export const updatePostCoverPicture = (postId: number, coverPictureUrl: string) => {
    return axios.patch<void>(`${ADMIN_PREFIX}/${postId}/updateCoverPicture`, {coverPictureUrl})
}

export const portalQueryBlogPost = (params: PortalQueryPostRequest) => {
    return axios.get<PageResponse<PostResponse>>(PORTAL_PREFIX, {params})
}

export const portalGetPostDetail = (postId: number) => {
    return axios.get<PostDetailResponse>(`${PORTAL_PREFIX}/${postId}`)
}

export default {
    queryPost,
    publishPost,
    removePost,
    syncPost,
    updatePostCoverPicture,
    portalQueryBlogPost,
    portalGetPostDetail
}