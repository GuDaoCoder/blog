import axios from "axios";

export function searchTag(params: SearchTagRequest) {
    return axios.get<PageResponse<TagResponse>>("/admin/tag", {params})
}

export function createTag(data: CreateTagRequest) {
    return axios.post<TagResponse>("/admin/tag", data)
}

export function updateTag(tagId: number, data: UpdateTagRequest) {
    return axios.patch<TagResponse>(`/admin/tag/${tagId}`, data)
}

export function deleteTag(tagId: number) {
    return axios.delete(`/admin/tag/${tagId}`)
}