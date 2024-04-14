import type {AxiosResponse} from "axios";
import axios from "axios";

const prefix = "/admin/tag";

export function searchTag(params: SearchTagRequest): Promise<AxiosResponse<PageResponse<TagResponse>>> {
    return axios.get<PageResponse<TagResponse>>(prefix, {params})
}
