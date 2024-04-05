import axios from "axios";

export function blogSearchTag(params: SearchTagRequest) {
    return axios.get<PageResponse<TagResponse>>("/blog/tags", {params})
}
