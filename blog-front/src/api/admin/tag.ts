import type {AxiosResponse} from "axios";
import axios from "axios";

const prefix = "/admin/tags";

export function searchTag(params: SearchTagRequest): Promise<AxiosResponse<PageResponse<TagResponse>>> {
    return axios.get<PageResponse<TagResponse>>(prefix, {params})
}
