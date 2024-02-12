import axios from "axios";

export function pageTag(params: PageTagDTO) {
    return axios.get<PageVO<PageTagVO>>("/admin/tag", {params})
}

export function createTag(data: TagDTO) {
    return axios.post<CreateTagVO>("/admin/tag", data)
}

export function updateTag(tagId: string, data: TagDTO) {
    return axios.put("/admin/tag/" + tagId, data)
}


export function deleteTag(tagId: string) {
    return axios.delete("/admin/tag/" + tagId)
}