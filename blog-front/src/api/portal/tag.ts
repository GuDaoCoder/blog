import axios from "axios";

const prefix = "/portal/tags";

export function blogSearchTag(params: SearchTagRequest) {
    return axios.get<PageResponse<TagResponse>>(prefix, {params})
}
